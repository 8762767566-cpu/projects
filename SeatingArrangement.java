import java.util.*;

/**
 * Classroom seating arrangement (plain Java, no external libraries).
 *
 * Compile:  javac SeatingArrangement.java
 * Run:      java SeatingArrangement          -> interactive menu
 *           java SeatingArrangement --demo   -> quick demo with sample students
 *
 * Seats are numbered by (row, column) starting from 1. Row 1 is the front.
 */

class Student {
    final String rollNo;
    final String name;

    Student(String rollNo, String name) {
        this.rollNo = rollNo;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + rollNo + ")";
    }
}

class SeatingChart {
    private final int rows, cols;
    private final Student[][] grid;                                   // seat -> student
    private final Map<String, Student> students = new TreeMap<>();    // rollNo -> student
    private final Map<String, int[]> seatOf = new HashMap<>();        // rollNo -> {row, col}

    SeatingChart(int rows, int cols) {
        if (rows < 1 || cols < 1) throw new IllegalArgumentException("Rows and columns must be at least 1.");
        this.rows = rows;
        this.cols = cols;
        this.grid = new Student[rows][cols];
    }

    // ---------- students ----------
    void addStudent(String rollNo, String name) {
        if (students.containsKey(rollNo))
            throw new IllegalArgumentException("Roll number '" + rollNo + "' already exists.");
        students.put(rollNo, new Student(rollNo, name));
    }

    void removeStudent(String rollNo) {
        requireStudent(rollNo);
        unassign(rollNo);
        students.remove(rollNo);
    }

    // ---------- seating ----------
    void assign(String rollNo, int row, int col) {
        Student s = requireStudent(rollNo);
        checkSeat(row, col);
        Student occupant = grid[row - 1][col - 1];
        if (occupant != null && occupant != s)
            throw new IllegalArgumentException("Seat (" + row + ", " + col + ") is already taken by " + occupant.name + ".");
        unassign(rollNo);                       // moves the student if already seated
        grid[row - 1][col - 1] = s;
        seatOf.put(rollNo, new int[]{row, col});
    }

    void unassign(String rollNo) {
        requireStudent(rollNo);
        int[] seat = seatOf.remove(rollNo);
        if (seat != null) grid[seat[0] - 1][seat[1] - 1] = null;
    }

    void swap(String rollA, String rollB) {
        requireStudent(rollA);
        requireStudent(rollB);
        int[] a = seatOf.get(rollA), b = seatOf.get(rollB);
        if (a == null || b == null)
            throw new IllegalArgumentException("Both students must already have seats to swap.");
        Student sa = grid[a[0] - 1][a[1] - 1], sb = grid[b[0] - 1][b[1] - 1];
        grid[a[0] - 1][a[1] - 1] = sb;
        grid[b[0] - 1][b[1] - 1] = sa;
        seatOf.put(rollA, b);
        seatOf.put(rollB, a);
    }

    int[] findSeat(String rollNo) {
        requireStudent(rollNo);
        return seatOf.get(rollNo);              // null if not seated
    }

    Student whoSits(int row, int col) {
        checkSeat(row, col);
        return grid[row - 1][col - 1];
    }

    List<Student> unseatedStudents() {
        List<Student> list = new ArrayList<>();
        for (Student s : students.values())
            if (!seatOf.containsKey(s.rollNo)) list.add(s);
        return list;
    }

    /** Fills empty seats (front to back, left to right). Returns {placed, leftOut}. */
    int[] autoAssign(boolean shuffle) {
        List<Student> waiting = unseatedStudents();
        if (shuffle) Collections.shuffle(waiting);
        int placed = 0;
        for (int r = 1; r <= rows && placed < waiting.size(); r++) {
            for (int c = 1; c <= cols && placed < waiting.size(); c++) {
                if (grid[r - 1][c - 1] == null) {
                    assign(waiting.get(placed).rollNo, r, c);
                    placed++;
                }
            }
        }
        return new int[]{placed, waiting.size() - placed};
    }

    // ---------- display ----------
    void show() {
        int w = 11;
        int total = (w + 1) * cols + 4;
        String title = " FRONT / BOARD ";
        int pad = Math.max(0, (total - title.length()) / 2);
        System.out.println();
        System.out.println("=".repeat(pad) + title + "=".repeat(pad));
        System.out.println();
        StringBuilder header = new StringBuilder("    ");
        for (int c = 1; c <= cols; c++) header.append(center("C" + c, w)).append(' ');
        System.out.println(header);
        for (int r = 1; r <= rows; r++) {
            StringBuilder line = new StringBuilder(String.format("R%-2d ", r));
            for (int c = 1; c <= cols; c++) {
                Student s = grid[r - 1][c - 1];
                String label = (s == null) ? "-" : s.name;
                if (label.length() > w) label = label.substring(0, w);
                line.append(center(label, w)).append(' ');
            }
            System.out.println(line);
        }
        System.out.println();
    }

    void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students added yet.");
            return;
        }
        for (Student s : students.values()) {
            int[] seat = seatOf.get(s.rollNo);
            String where = (seat == null) ? "unseated" : "Row " + seat[0] + ", Col " + seat[1];
            System.out.printf("%-8s %-15s %s%n", s.rollNo, s.name, where);
        }
    }

    // ---------- helpers ----------
    private Student requireStudent(String rollNo) {
        Student s = students.get(rollNo);
        if (s == null) throw new IllegalArgumentException("No student with roll number '" + rollNo + "'.");
        return s;
    }

    private void checkSeat(int row, int col) {
        if (row < 1 || row > rows || col < 1 || col > cols)
            throw new IllegalArgumentException("Seat (" + row + ", " + col + ") is outside the classroom ("
                    + rows + " rows x " + cols + " columns).");
    }

    private static String center(String text, int width) {
        if (text.length() >= width) return text;
        int left = (width - text.length()) / 2;
        int right = width - text.length() - left;
        return " ".repeat(left) + text + " ".repeat(right);
    }
}

public class SeatingArrangement {

    static void demo() {
        SeatingChart chart = new SeatingChart(3, 4);
        String[] names = {"Asha", "Ravi", "Meena", "Kiran", "Divya", "Arjun", "Sneha", "Vikram", "Lakshmi", "Rahul"};
        for (int i = 0; i < names.length; i++)
            chart.addStudent(String.format("S%02d", i + 1), names[i]);

        int[] result = chart.autoAssign(false);
        System.out.println("Auto-assigned " + result[0] + " students, " + result[1] + " without seats.");
        chart.show();

        chart.swap("S01", "S10");
        System.out.println("After swapping Asha (S01) and Rahul (S10):");
        chart.show();

        chart.assign("S05", 3, 4);
        int[] seat = chart.findSeat("S05");
        System.out.println("Seat of S05: Row " + seat[0] + ", Column " + seat[1]);
        System.out.println("Who sits at (1, 1)? " + chart.whoSits(1, 1));

        try {
            chart.assign("S02", 1, 1);          // taken seat -> shows the error handling
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void menu() {
        Scanner in = new Scanner(System.in);
        int rows = readInt(in, "Rows in classroom: ");
        int cols = readInt(in, "Columns in classroom: ");
        SeatingChart chart = new SeatingChart(rows, cols);

        String actions = "\n1) Add student          5) Show seating chart\n"
                + "2) Remove student       6) Find a student's seat\n"
                + "3) Assign / move seat   7) Auto-assign unseated students\n"
                + "4) Swap two students    8) List all students\n"
                + "9) Who sits at a seat?  0) Quit\n";

        while (true) {
            System.out.println(actions);
            System.out.print("Choose: ");
            String choice = in.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> chart.addStudent(readLine(in, "Roll no: "), readLine(in, "Name: "));
                    case "2" -> chart.removeStudent(readLine(in, "Roll no: "));
                    case "3" -> chart.assign(readLine(in, "Roll no: "), readInt(in, "Row: "), readInt(in, "Column: "));
                    case "4" -> chart.swap(readLine(in, "First roll no: "), readLine(in, "Second roll no: "));
                    case "5" -> chart.show();
                    case "6" -> {
                        int[] seat = chart.findSeat(readLine(in, "Roll no: "));
                        System.out.println(seat == null ? "Not seated yet." : "Row " + seat[0] + ", Column " + seat[1]);
                    }
                    case "7" -> {
                        boolean shuffle = readLine(in, "Shuffle randomly? (y/n): ").equalsIgnoreCase("y");
                        int[] r = chart.autoAssign(shuffle);
                        System.out.println("Placed " + r[0] + "; " + r[1] + " could not be seated (no empty seats).");
                    }
                    case "8" -> chart.listStudents();
                    case "9" -> {
                        Student s = chart.whoSits(readInt(in, "Row: "), readInt(in, "Column: "));
                        System.out.println(s == null ? "Empty seat." : s.toString());
                    }
                    case "0" -> { return; }
                    default -> System.out.println("Please choose a number from the menu.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static String readLine(Scanner in, String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }

    private static int readInt(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--demo")) demo();
        else menu();
    }
}

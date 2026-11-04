
import java.util.*;

public class studdemo {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name : ");
        String Name = sc.nextLine();
        System.out.print("Enter your Roll no.: ");
        int rollno = sc.nextInt();
        System.out.print("Enter Your Course : ");
        String Course = sc.next();
        System.out.print("enter your age : ");
        int Age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter the number of subjects chosen by the student : ");
        int NoOfSubjects = sc.nextInt();
        sc.nextLine();
        String subjects[] = new String[NoOfSubjects];
        int marks[] = new int[NoOfSubjects];
        int total = 0;
        int totalforeach = 100;
        int passed = 0;
        int failed = 0;
        int passmark = 35;
        String Choice = "0";
        boolean marksentered = false;
        System.out.println();
        System.out.println("Entered Details Are Stored Successfully.........");
        System.out.println();
        do//27
        {
            System.out.println("The Menu  As Follows \n" + " 1. Enter Student Details \n" + " 2. Enter Subject Marks \n" + " 3. Display Student Report \n" + " 4. Search Subject \n" + " 5. Exit");
            System.out.print("Enter your choice : ");
            Choice = sc.next();
            sc.nextLine();
            switch (Choice) {
                case "1":
                    System.out.println("-------------------------------------------------");
                    System.out.println("Name of the Student                       : " + Name);
                    System.out.println("Age of the student                        : " + Age);
                    System.out.println("Roll number of the student                : " + rollno);
                    System.out.println("Name of the course                        : " + Course);
                    System.out.println("Number of Subjects Choosen by the Student : " + NoOfSubjects);
                    System.out.println("-------------------------------------------------");
                    System.out.println();
                    break;//43

                case "2":
                    System.out.println("==================================");
                    for (int i = 0; i < NoOfSubjects; i++) {
                        System.out.print("Enter the subject name : ");
                        subjects[i] = sc.nextLine();
                    }
                    System.out.println("----------------------------------");
                    for (int i = 0; i < NoOfSubjects; i++) {
                        System.out.print("Enter the marks for " + subjects[i] + " : ");
                        marks[i] = Integer.parseInt(sc.nextLine());
                        total += marks[i];
                        marksentered = true;
                    }
                    System.out.println("==================================");
                    System.out.println();
                    break;//61

                case "3":
                    System.out.println();
                    if (!marksentered) {
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        System.out.println("Please Enter the name and the marks of the Subjects through Option 2:");
                    } else {
                        System.out.println("================================================");
                        System.out.println("          STUDENT REPORT          ");
                        System.out.println("================================================");
                        System.out.println();

                        String Highsub = subjects[0];
                        String Lowsub = subjects[0];
                        int greatest = marks[0];
                        int lowest = marks[0];
                        for (int i = 1; i < NoOfSubjects; i++) {
                            if (marks[i] < lowest) {
                                lowest = marks[i];
                                Lowsub = subjects[i];
                            } else if (marks[i] > greatest) {
                                greatest = marks[i];
                                Highsub = subjects[i];
                            }
                        }
                        for (int i = 0; i < marks.length; i++) {

                            if (marks[i] < passmark) {
                                failed++;
                            } else if (marks[i] >= passmark) {
                                passed++;
                            }
                        }
                        System.out.println("STUDENT NAME : " + Name);
                        System.out.println("AGE          : " + Age);
                        System.out.println("ROLL NUMBER   : " + rollno);
                        System.out.println("COURSE        : " + Course);
                        System.out.println("------------------------------------------------");
                        System.out.println("   MARKS OF THE RESPECTIVE SUBJECTS ");
                        System.out.println("------------------------------------------------");

                        for (int i = 0; i < NoOfSubjects; i++) {
                            System.out.println("Marks of " + subjects[i] + " is " + marks[i]);
                        }
                        System.out.println("------------------------------------------------");
                        System.out.println("The Total Of The Marks : " + total);
                        System.out.println("The Total Marks For Each Subject : " + totalforeach);
                        int avg = total / NoOfSubjects;
                        double percent = total / NoOfSubjects;
                        System.out.println("Average mark is : " + avg);
                        System.out.println("Percentage      : " + percent + "%");
                        System.out.println("Highest Marks   : " + greatest);
                        System.out.println("Lowest Marks    : " + lowest);
                        System.out.println();

                        System.out.println("Number Of Passed Subjects : " + passed);
                        System.out.println("Number Of Failed Subjects : " + failed);
                        System.out.println();
                        if (percent < 100 && percent >= 90) {
                            System.out.println("The Grade of the Student : A+");
                        } else if (percent >= 80 && percent < 90) {
                            System.out.println("The Grade of the Student : A");
                        } else if (percent >= 70 && percent < 80) {
                            System.out.println("The Grade of the Student : B");
                        } else if (percent >= 60 && percent < 70) {
                            System.out.println("The Grade of the Student : C");
                        } else if (percent >= 50 && percent < 60) {
                            System.out.println("The Grade of the Student : D");
                        } else if (percent < 50) {
                            System.out.println("The Grade of the Student : E");
                        } else {
                            System.out.println("invalid percentage.....  Enter valid 				percentage value.....");
                        }
                        System.out.println();

                        boolean Passed = true;
                        for (int i = 0; i < NoOfSubjects; i++) {
                            if (marks[i] < 35) {
                                Passed = false;
                            }
                        }
                        if (Passed) {
                            System.out.println("          Result : Pass ");
                        } else {
                            System.out.println("                44Result : Fail ");
                        }

                        System.out.println("================================================");
                    }
                    System.out.println();
                    break;//84

                case "4":
                    if (!marksentered) {
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        System.out.println("Please Enter the name and the marks of the Subjects through Option 2:");

                    } else {

                        System.out.println("------------------------------------------------------");

                        System.out.print("Enter the subject name to know your marks : ");
                        String sub = sc.nextLine();
                        boolean found = false;
                        for (int i = 0; i < NoOfSubjects; i++) {
                            if (subjects[i].equals(sub)) {
                                System.out.println(" Marks of the " + subjects[i] + " : " + marks[i]);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            System.out.println("The subject is not found.....Enter correct subject");
                        }
                    }
                    System.out.println("------------------------------------------------------");
                    System.out.println();
                    break;

                case "5":

                    System.out.println("---------------------------------------------");
                    System.out.println("---------------------------------------------");
                    System.out.println("You are out of the Menu !!!!!!!");
                    System.out.println();
                    break;

                default:
                    //System.out.println("---------------------------------------------");
                    System.out.println("**************-----------------.............");
                    System.out.println("Invalid Choice.....Enter a valid Choice");
                    System.out.println();
                    break;
            }
        } while (!Choice.equals("5"));
    }
}

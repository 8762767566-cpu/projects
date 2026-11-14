class recursionTOH{
    public static void transfer(int n,String src , String help,String dest)
    {
        if(n==1)
        {
            System.out.println(" Disc "+n+" from "+src+" to "+dest);
            return;

        }
        transfer(n-1,src,dest,help);
        System.out.println("Disc "+n+" from "+src+" to "+dest);
        transfer(n-1,help,src,dest);
        System.out.println("Disc "+n+"from"+src+"to"+dest);
    }
    public static void main(String args[])
    {
        int n = 3;
        transfer(n,"A","B","C");
    }
}
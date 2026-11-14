class recursionlarge
{
    public static void move(String str,int idx,int count,String newstring)
    {
        if(idx == str.length())
        {
            for(int i=0;i<count;i++)
            {
                newstring += 'x';
            }
            System.out.println(newstring);
            return;
        }
        char cur = str.charAt(idx);
        if(cur == 'x')
        {
            count++;
            move(str,idx+1,count,newstring);
        }
        else
        {
            newstring += cur;
            move(str,idx+1,count,newstring);
        }
    }
    public static void main(String args[])
    {
        String str = "axbcxxd";
        move(str,0,0,"");
    }
}
class recursionsorted
{
    public static boolean issorted(int arr[] , int n)
    {
        if(n == arr.length - 1)
        {
            return true;
        }
        if(arr[n] < arr[n+1])
        {
            return issorted(arr,n+1);
        }
        else{
            return false;
        }
    
    }
    public static void main(String args[])
    {
        int arr[] = {1,2,3};
        System.out.println(issorted(arr,0));
    }
}
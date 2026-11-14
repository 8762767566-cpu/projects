class recursion
{
	public static int pow(int x , int n )
	{
		if(x == 0)
		{
			return 0;
		}
		if (n == 0)
		{
			return 1;
		}
		int pow0 = x*pow(x,n-1);
		return pow0;
	}
	public static void main(String args[])
	{
		int n = 5;
		int x = 2;
		int ans = pow(x,n);
		System.out.println("POWER :"+ans);
	}
}		
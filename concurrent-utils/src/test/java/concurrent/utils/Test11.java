package concurrent.utils;



public class Test11 {

	public static void main(String[] args) {
		System.out.println(SingleTon.getInstance());
	}
}

class SingleTon{
	private SingleTon(){
		
	}
	private static class SingleTonHelper{
		private final static SingleTon INSTANCE=new SingleTon();
	}
	public static SingleTon getInstance(){
		return SingleTonHelper.INSTANCE;
	}
}

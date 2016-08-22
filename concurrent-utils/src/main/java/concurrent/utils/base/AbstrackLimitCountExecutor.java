package concurrent.utils.base;


public abstract class AbstrackLimitCountExecutor<T>  {

	private T data;
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public AbstrackLimitCountExecutor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AbstrackLimitCountExecutor(T data) {
		super();
		this.data = data;
	}
	public Object execute(T data){
		
		return null;
	}

	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}
}

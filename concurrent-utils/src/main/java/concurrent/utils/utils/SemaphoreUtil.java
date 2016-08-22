package concurrent.utils.utils;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import concurrent.utils.base.AbstrackLimitCountExecutor;
import concurrent.utils.base.BaseSemaphore;


public class SemaphoreUtil<T> extends BaseSemaphore {


	public SemaphoreUtil(int permits, boolean fair) {
		super(permits, fair);
	}

	public Object execute(AbstrackLimitCountExecutor executor) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unused")
		class ExecuteThread implements Runnable{

			private AbstrackLimitCountExecutor executor;
			private Semaphore semaphore;
			@SuppressWarnings("unused")
			public ExecuteThread(AbstrackLimitCountExecutor executor,
					Semaphore semaphore) {
				super();
				this.executor = executor;
				this.semaphore = semaphore;
			}

			public void run() {
				// TODO Auto-generated method stub
				try {
					if(this.semaphore.tryAcquire(10, TimeUnit.SECONDS)){
						//System.out.println("acquire:"+semaphore.availablePermits());
						if(executor == null ){
							return;
						}
						executor.execute(this.executor.getData());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}finally{
					this.semaphore.release();
					//System.out.println("release:"+semaphore.availablePermits());
				}
			}
		}
		//new Thread(new ExecuteThread(executor, semaphore)).start();
		ExecuteThread thread =new ExecuteThread(executor, semaphore);
		executorService.execute(thread);
		return null;
	}


}

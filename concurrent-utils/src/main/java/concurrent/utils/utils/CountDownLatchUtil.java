package concurrent.utils.utils;

import java.util.concurrent.CountDownLatch;

import concurrent.utils.base.AbstrackLimitCountExecutor;
import concurrent.utils.base.BaseCountDownLatch;

public class CountDownLatchUtil extends BaseCountDownLatch {

	public CountDownLatchUtil(CountDownLatch countDownLatch,int count) {
		super(countDownLatch,count);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object execute(AbstrackLimitCountExecutor executor) {
		@SuppressWarnings("unused")
		class ExecuteThread implements Runnable {

			private AbstrackLimitCountExecutor executor;
			private CountDownLatch countDownLatch;

			@SuppressWarnings("unused")
			public ExecuteThread(AbstrackLimitCountExecutor executor,
					CountDownLatch countDownLatch) {
				super();
				this.executor = executor;
				this.countDownLatch = countDownLatch;
			}

			public void run() {
				// TODO Auto-generated method stub
				try {

					if (executor == null) {
						return;
					}
					executor.execute(executor.getData());
					
				} catch (Exception e) {
					// TODO: handle exception
				}finally{
					//System.out.println(this.countDownLatch.getCount());
					this.countDownLatch.countDown();
					//System.out.println(this.countDownLatch.getCount());
				}
			}
		}
		// new Thread(new ExecuteThread(executor, countDownLatch)).start();
		ExecuteThread thread = new ExecuteThread(executor, countDownLatch);
		executorService.execute(thread);
		return null;
	}
}

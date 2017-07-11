package concurrent.utils.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import concurrent.utils.base.AbstrackLimitCountExecutor;
import concurrent.utils.base.BaseCyclicBarrier;

public class CyclicBarrierUtil extends BaseCyclicBarrier {

	public CyclicBarrierUtil(int permits) {
		super(permits);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object execute(AbstrackLimitCountExecutor executor) {
		@SuppressWarnings("unused")
		class ExecuteThread implements Runnable {

			private final AbstrackLimitCountExecutor executor;
			private final CyclicBarrier cyclicBarrier;

			@SuppressWarnings("unused")
			public ExecuteThread(AbstrackLimitCountExecutor executor,
					CyclicBarrier cyclicBarrier) {
				super();
				this.executor = executor;
				this.cyclicBarrier = cyclicBarrier;
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
				} finally {
					try {
						this.cyclicBarrier.await(1000*threadCount,TimeUnit.SECONDS);
						System.out.println("over");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		// new Thread(new ExecuteThread(executor, semaphore)).start();
		ExecuteThread thread = new ExecuteThread(executor, this.cyclicBarrier);
		executorService.execute(thread);
		return null;
	}

}

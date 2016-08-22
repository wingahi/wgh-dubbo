package concurrent.utils.base;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;


public abstract class BaseCountDownLatch extends BaseTask {
	
	
	protected CountDownLatch countDownLatch = null;
	public BaseCountDownLatch(CountDownLatch countDownLatch,int count) {
		super(count);
		this.countDownLatch = countDownLatch;
	}

}

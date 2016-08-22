package concurrent.utils;

import concurrent.utils.base.AbstrackLimitCountExecutor;
import concurrent.utils.base.BaseSemaphore;
import concurrent.utils.utils.SemaphoreUtil;

public class SemaphoreUtilTest {
	static BaseSemaphore taskExecutorUtil = new SemaphoreUtil<TestEntity>(3, true);
	public static void main(String[] args) {
		
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					TestEntity testEntity = new TestEntity();
					testEntity.setName("ddddddddd"+System.currentTimeMillis());
					taskExecutorUtil.execute(new AbstrackLimitCountExecutor<TestEntity>(testEntity) {
						public Object execute(TestEntity testEntity) {
							// TODO Auto-generated method stub
							System.out.println("thread-name:"+Thread.currentThread()+",testEntity"+testEntity.getName());
							return null;
						}
						
					});
				}
			}).start();
		}
		
	}
}

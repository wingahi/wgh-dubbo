package concurrent.utils;

import java.util.concurrent.CountDownLatch;

import concurrent.utils.base.AbstrackLimitCountExecutor;
import concurrent.utils.base.BaseCountDownLatch;
import concurrent.utils.base.BaseSemaphore;
import concurrent.utils.utils.CountDownLatchUtil;
import concurrent.utils.utils.CountDownLatchUtils;
import concurrent.utils.utils.SemaphoreUtil;

public class CountDownLatchUtilsTest {

	static CountDownLatchUtils taskExecutorUtil = new CountDownLatchUtils();

	public static void main(String[] args) {

		for (int i = 0; i < 5; i++) {

			TestEntity testEntity = new TestEntity();
			testEntity.setName("ddddddddd" + i);
			taskExecutorUtil
					.AddExecutor(new AbstrackLimitCountExecutor<TestEntity>(
							testEntity) {
						public Object execute(TestEntity testEntity) {
							// TODO Auto-generated method stub
							System.out.println("thread-name:"
									+ Thread.currentThread() + ",testEntity"
									+ testEntity.getName());
							return null;
						}

			});
		}
		taskExecutorUtil.execute();

		System.out.println("over");
	}
}

package concurrent.utils.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import concurrent.utils.base.AbstrackLimitCountExecutor;

public class CountDownLatchUtils {

	private List<AbstrackLimitCountExecutor> executors;
	private CountDownLatch countDownLatch;
	private CountDownLatchUtil countDownLatchUtil;

	public CountDownLatchUtils() {
		// TODO Auto-generated constructor stub
	}
	public CountDownLatchUtils(List<AbstrackLimitCountExecutor> executors) {
		this.executors = executors;
	}
	
	public void AddExecutor(AbstrackLimitCountExecutor executor) {
		if(this.executors==null){
			this.executors = new ArrayList<AbstrackLimitCountExecutor>();
		}
		this.executors.add(executor);
	}
	
	public Object execute() {
		// TODO Auto-generated method stub
		if(executors==null || executors.size()==0) return null;
		countDownLatch = new CountDownLatch(executors.size());
		countDownLatchUtil = new CountDownLatchUtil(countDownLatch, executors.size());
		for (AbstrackLimitCountExecutor executor : executors) {
			countDownLatchUtil.execute(executor);
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

package netty.common.stack;

public class DemonStackConfiguration {
	private DemonStackModel model;
	private boolean enableHaxTracer;
	private boolean enableSignallingTracer;
	private int workThreadCount;
	
	public DemonStackConfiguration(){
		model=DemonStackModel.Mutiplex;
		enableHaxTracer=false;
		enableSignallingTracer=true;
		workThreadCount=0;
	}

	public DemonStackModel getModel() {
		return model;
	}

	public void setModel(DemonStackModel model) {
		this.model = model;
	}

	public boolean isEnableHaxTracer() {
		return enableHaxTracer;
	}

	public void setEnableHaxTracer(boolean enableHaxTracer) {
		this.enableHaxTracer = enableHaxTracer;
	}

	public boolean isEnableSignallingTracer() {
		return enableSignallingTracer;
	}

	public void setEnableSignallingTracer(boolean enableSignallingTracer) {
		this.enableSignallingTracer = enableSignallingTracer;
	}

	public int getWorkThreadCount() {
		return workThreadCount;
	}

	public void setWorkThreadCount(int workThreadCount) {
		this.workThreadCount = workThreadCount;
	}

	public boolean getEnableHexTracer() {
		// TODO Auto-generated method stub
		return false;
	}
}





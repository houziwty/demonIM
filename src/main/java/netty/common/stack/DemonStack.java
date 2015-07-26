package netty.common.stack;

import netty.common.tracer.DemonTracer;

public class DemonStack {
	
	private static DemonTracer _tracer=DemonTracer.getInstance(DemonStack.class);
	private static DemonStack _instance;
	private DemonStackConfiguration _config;
	
}

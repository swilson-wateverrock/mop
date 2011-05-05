package test;


import helpers.AbstractFSMMonitorTestTemplate;
import helpers.ConnectionClosedMonitorTemplate;
import junit.framework.Assert;

import org.junit.Test;

import de.bodden.mopbox.generic.IVariableBinding;
import de.bodden.mopbox.generic.def.VariableBinding;

/**
 * Tests for the {@link ConnectionClosedMonitorTemplate}. 
 */
public class ConnectionClosed extends AbstractTest {

	IVariableBinding<String,Object> v,v2;
	
	public ConnectionClosed() {
		v = new VariableBinding<String,Object>();
		v.put("c", new Object() {
			@Override
			public String toString() {
				return "c1";
			}
		});
		v2 = new VariableBinding<String,Object>();
		v2.put("c", new Object() {
			@Override
			public String toString() {
				return "c2";
			}
		});}
	
	protected AbstractFSMMonitorTestTemplate<String,String,Object> makeTemplate() {
		return new ConnectionClosedMonitorTemplate();
	}


	@Test
	public void testSimple() {
		template.processEvent("close",v);
		template.processEvent("write",v2);
		template.processEvent("close",v2);
		template.processEvent("write",v2);
		template.processEvent("write",v);	
		Assert.assertEquals("{c=c1}", template.getTrace());
	}

	@Test
	public void testInterleaving() {
		template.processEvent("close",v);
		template.processEvent("write",v2);
		template.processEvent("close",v2);
		template.processEvent("write",v2);
		template.processEvent("write",v);	
		Assert.assertEquals("{c=c1}", template.getTrace());
	}

	@Test
	public void testTwoMatches() {
		template.processEvent("close",v);
		template.processEvent("close",v2);
		template.processEvent("write",v2);
		template.processEvent("write",v);	
		Assert.assertEquals("{c=c2}{c=c1}", template.getTrace());
	}

	@Test
	public void testWriteFirst() {
		template.processEvent("write",v);
		template.processEvent("close",v);
		template.processEvent("write",v);	
		Assert.assertEquals("", template.getTrace());
	}

}

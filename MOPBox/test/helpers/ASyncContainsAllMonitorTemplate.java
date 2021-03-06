package helpers;

import de.bodden.mopbox.finitestate.State;
import de.bodden.mopbox.generic.IAlphabet;

/**
 * This is a concrete monitor template for the following regular-expression property:
 * <code>containsAll* sync(c1) containsAll(c1)* sync(c2) containsAll(c2)</code> 
 */
public class ASyncContainsAllMonitorTemplate extends AbstractFSMMonitorTestTemplate<String,String,Object> {
	
	@Override
	protected void fillAlphabet(IAlphabet<String,String> a) {
		a.makeNewSymbol("sync");
		a.makeNewSymbol("containsAll");
	}

	@Override
	protected State<String> setupStatesAndTransitions() {
		State<String> initial = makeState(false);
		State<String> synched1 = makeState(false);
		State<String> synched2 = makeState(false);
		State<String> error = makeState(true);
		
		initial.addTransition(getSymbolByLabel("containsAll"), initial);
		initial.addTransition(getSymbolByLabel("sync"), synched1);
		synched1.addTransition(getSymbolByLabel("containsAll"), synched1);
		synched1.addTransition(getSymbolByLabel("sync"), synched2);
		synched2.addTransition(getSymbolByLabel("containsAll"), error);
		return initial;
	}

}

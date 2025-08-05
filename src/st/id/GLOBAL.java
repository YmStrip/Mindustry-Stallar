package st.id;


import st.id.entity.ID;
import st.id.entity.IDGroup;

import java.util.HashMap;

public class GLOBAL {
	public static IDGroup<ID> GROUP = new IDGroup<>();
	public static HashMap<Object,Class<ID>> CLASS = new HashMap<>();
}
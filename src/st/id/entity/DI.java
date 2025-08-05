package st.id.entity;



import java.util.ArrayList;
import java.util.HashMap;

public class DI {
	public HashMap<Class<Object>, DIStore> store = new HashMap<>();
	public HashMap<Class<DIStore>, ArrayList<Class<?>>> registry = new HashMap<>();
	public void instance(Object object) {
	}
}

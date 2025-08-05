package st.mod.qio.entity;


import st.id.anno.KeyInclude;
import st.id.entity.ID;

public class QNetwork extends ID {
	@KeyInclude
	public QMap LIQUID = new QMap() ;
	@KeyInclude
	public QMap ITEM = new QMap();
	@KeyInclude
	public QMap UNIT = new QMap();
	@KeyInclude
	public QMap PAYLOAD = new QMap();
}

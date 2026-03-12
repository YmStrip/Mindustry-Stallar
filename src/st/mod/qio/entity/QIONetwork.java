package st.mod.qio.entity;


import st.id.anno.KeyInclude;
import st.id.entity.ID;

public class QIONetwork extends ID {
	@KeyInclude
	public QMap Liquid = new QMap() ;
	@KeyInclude
	public QMap Item = new QMap();
	@KeyInclude
	public QMap Unit = new QMap();
	@KeyInclude
	public QMap Payload = new QMap();
}

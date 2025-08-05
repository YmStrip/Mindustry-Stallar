package st.mod.util;


import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.SectorPreset;
import mindustry.world.Block;

public class STechBuilder {
	public TechTree.TechNode tech;
	public UnlockableContent content;
	public UnlockableContent parent;

	public STechBuilder parent(UnlockableContent item) {
		if (content instanceof Block b) {
			if (b.researchCostMultiplier <= 0) b.researchCostMultiplier = 1;
		}

		parent = item;
		tech = new TechTree.TechNode(item.techNode, content, content.researchRequirements());
		if ((content instanceof Item) || (content instanceof Liquid)) {
			require(new Objectives.Produce(content));
		}
		if ((content instanceof SectorPreset) && (item instanceof SectorPreset s)) {
			require(new Objectives.SectorComplete(s));
		}
		return this;
	}

	public STechBuilder(UnlockableContent item) {
		content = item;
	}
	public STechBuilder(UnlockableContent parent, UnlockableContent item) {
		this(item);
		this.parent(parent);
	}


	//并行的
	public STechBuilder next(UnlockableContent item, child child) {
		var v = new STechBuilder(item);
		v.parent(parent);
		child.child(v);
		return this;
	}

	public STechBuilder next(UnlockableContent item) {
		return next(item, (t) -> {
		});
	}

	public STechBuilder require(ItemStack[] itemStack) {
		tech.requirements = itemStack;
		return this;
	}

	public STechBuilder require(UnlockableContent item) {
		return require(new Objectives.Research(item));
	}

	public STechBuilder require(Objectives.Objective o) {
		tech.objectives.add(o);
		return this;
	}

	@FunctionalInterface
	public interface child {
		void child(STechBuilder t);
	}
	/**
	 * add child , and return current builder
	 * @param item content
	 * @return current builder
	 */
	public STechBuilder add(UnlockableContent item) {
		this.add(item, (t) -> {});
		return this;
	}

	public STechBuilder add(UnlockableContent item, child child) {
		var v = new STechBuilder(item);
		v.parent(content);
		child.child(v);
		return this;
	}
	/**
	 * add child , and return child tech builder
	 * @param item child content
	 * @return child builder
	 */
	public STechBuilder children(UnlockableContent item) {
		var v = new STechBuilder(item);
		v.parent(content);
		return v;
	}
}

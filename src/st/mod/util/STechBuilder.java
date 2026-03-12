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
	public TechTree.TechNode Tech;
	public UnlockableContent Content;
	public UnlockableContent Parent;

	public STechBuilder Parent(UnlockableContent item) {
		if (Content instanceof Block b) {
			if (b.researchCostMultiplier <= 0) b.researchCostMultiplier = 1;
		}

		Parent = item;
		Tech = new TechTree.TechNode(item.techNode, Content, Content.researchRequirements());
		if ((Content instanceof Item) || (Content instanceof Liquid)) {
			Require(new Objectives.Produce(Content));
		}
		if ((Content instanceof SectorPreset) && (item instanceof SectorPreset s)) {
			Require(new Objectives.SectorComplete(s));
		}
		return this;
	}

	public STechBuilder(UnlockableContent item) {
		Content = item;
	}
	public STechBuilder(UnlockableContent parent, UnlockableContent item) {
		this(item);
		this.Parent(parent);
	}

	public STechBuilder Next(UnlockableContent item, Ch child) {
		var v = new STechBuilder(item);
		v.Parent(Parent);
		child.Child(v);
		return this;
	}
	public STechBuilder Next(UnlockableContent item) {
		return Next(item, (t) -> {
		});
	}
	public STechBuilder Require(ItemStack[] itemStack) {
		Tech.requirements = itemStack;
		return this;
	}
	public STechBuilder Require(UnlockableContent item) {
		return Require(new Objectives.Research(item));
	}
	public STechBuilder Require(Objectives.Objective o) {
		Tech.objectives.add(o);
		return this;
	}
	@FunctionalInterface
	public interface Ch {
		void Child(STechBuilder t);
	}
	/**
	 * add child , and return current builder
	 * @param item content
	 * @return current builder
	 */
	public STechBuilder Add(UnlockableContent item) {
		this.Add(item, (t) -> {});
		return this;
	}
	public STechBuilder Add(UnlockableContent item, Ch child) {
		var v = new STechBuilder(item);
		v.Parent(Content);
		child.Child(v);
		return this;
	}
	/**
	 * add child , and return child tech builder
	 * @param item child content
	 * @return child builder
	 */
	public STechBuilder Children(UnlockableContent item) {
		var v = new STechBuilder(item);
		v.Parent(Content);
		return v;
	}
}

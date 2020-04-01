package cz.jalasoft.template.engine;

import cz.jalasoft.template.engine.odt.OdtTemplateEngine;

public class Main {

	public static void main(String[] args) throws Exception {

		TemplateSource template = TemplateSource.from("/home/lastovicka/Documents/sablona_vypis_z_uctu.odt");
		DataModel model = YamlDataModel.fromFile("/home/lastovicka/Documents/data.yml");

		OdtTemplateEngine engine = new OdtTemplateEngine();

		Document doc = engine.compile(template, model);

		doc.toFile("/home/lastovicka/Documents/copy1.odt");
	}
}

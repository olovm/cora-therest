package epc.therest.data.converter.spider;

import epc.spider.data.Action;
import epc.spider.data.SpiderDataAtomic;
import epc.spider.data.SpiderDataElement;
import epc.spider.data.SpiderDataGroup;
import epc.spider.data.SpiderDataRecord;
import epc.therest.data.ActionLink;
import epc.therest.data.RestDataGroup;
import epc.therest.data.RestDataRecord;
import epc.therest.data.converter.ConverterException;

public final class DataRecordSpiderToRestConverter {

	private SpiderDataRecord spiderDataRecord;
	private String baseURL;
	private SpiderDataGroup spiderDataGroup;
	private RestDataRecord restDataRecord;

	public static DataRecordSpiderToRestConverter fromSpiderDataRecordWithBaseURL(
			SpiderDataRecord spiderDataRecord, String baseURL) {
		return new DataRecordSpiderToRestConverter(spiderDataRecord, baseURL);
	}

	private DataRecordSpiderToRestConverter(SpiderDataRecord spiderDataRecord, String baseURL) {
		this.spiderDataRecord = spiderDataRecord;
		this.baseURL = baseURL;
	}

	public RestDataRecord toRest() {

		spiderDataGroup = spiderDataRecord.getSpiderDataGroup();
		DataGroupSpiderToRestConverter dataGroupSpiderToRestConverter = DataGroupSpiderToRestConverter
				.fromSpiderDataGroup(spiderDataGroup);
		RestDataGroup restDataGroup = dataGroupSpiderToRestConverter.toRest();
		restDataRecord = RestDataRecord.withRestDataGroup(restDataGroup);

		if (hasActions()) {
			convertActionsToLinks();
		}
		if (!spiderDataRecord.getKeys().isEmpty()) {
			for (String string : spiderDataRecord.getKeys()) {
				restDataRecord.addKey(string);
			}
		}
		return restDataRecord;
	}

	private boolean hasActions() {
		return !spiderDataRecord.getActions().isEmpty();
	}

	private void convertActionsToLinks() {
		SpiderDataGroup recordInfo = findRecordInfo();
		String id = findId(recordInfo);
		String type = findType(recordInfo);
		createRestLinks(id, type);
	}

	private SpiderDataGroup findRecordInfo() {
		SpiderDataGroup recordInfo = null;
		for (SpiderDataElement spiderDataElement : spiderDataGroup.getChildren()) {
			if ("recordInfo".equals(spiderDataElement.getDataId())) {
				recordInfo = (SpiderDataGroup) spiderDataElement;
				break;
			}
		}
		if (null == recordInfo) {
			throw new ConverterException("No recordInfo found convertion not possible");
		}
		return recordInfo;
	}

	private String findId(SpiderDataGroup recordInfo) {
		String id = "";
		for (SpiderDataElement spiderDataElement : recordInfo.getChildren()) {
			if ("id".equals(spiderDataElement.getDataId())) {
				id = ((SpiderDataAtomic) spiderDataElement).getValue();
			}
		}
		if ("".equals(id)) {
			throw new ConverterException("No id was found in recordInfo convertion not possible");
		}
		return id;
	}

	private String findType(SpiderDataGroup recordInfo) {
		String type = "";
		for (SpiderDataElement spiderDataElement : recordInfo.getChildren()) {
			if ("type".equals(spiderDataElement.getDataId())) {
				type = ((SpiderDataAtomic) spiderDataElement).getValue();
			}
		}
		if ("".equals(type)) {
			throw new ConverterException("No type was found in recordInfo convertion not possible");
		}
		return type;
	}

	private void createRestLinks(String id, String type) {
		String url = type + "/" + id;

		for (Action action : spiderDataRecord.getActions()) {
			ActionLink actionLink = ActionLink.withAction(action);

			actionLink.setURL(baseURL + url);
			// TODO: add path etc.
			if (Action.READ.equals(action)) {
				actionLink.setRequestMethod("GET");
			} else if (Action.UPDATE.equals(action)) {
				actionLink.setRequestMethod("POST");
			} else {
				actionLink.setRequestMethod("DELETE");
			}

			actionLink.setAccept("application/uub+record+json");
			actionLink.setContentType("application/uub+record+json");
			restDataRecord.addActionLink(action.name().toLowerCase(), actionLink);
		}
	}
}

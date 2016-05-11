/*
 * Copyright 2015 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.uu.ub.cora.therest.testdata;

import se.uu.ub.cora.bookkeeper.data.DataAtomic;
import se.uu.ub.cora.bookkeeper.data.DataGroup;
import se.uu.ub.cora.spider.record.storage.RecordStorage;
import se.uu.ub.cora.storage.RecordStorageInMemory;

public class TestDataRecordInMemoryStorage {
	public static RecordStorageInMemory createRecordStorageInMemoryWithTestData() {
		RecordStorageInMemory recordsInMemory = new RecordStorageInMemory();
		addPlace(recordsInMemory);
		addSecondPlace(recordsInMemory);
		addMetadata(recordsInMemory);
		addPresentation(recordsInMemory);
		addText(recordsInMemory);
		addRecordType(recordsInMemory);
		addRecordTypeRecordType(recordsInMemory);
		addRecordTypeRecordTypeAutoGeneratedId(recordsInMemory);
		addRecordTypePlace(recordsInMemory);
		addAbstractRecordTypes(recordsInMemory);
		addRecordTypeBadType(recordsInMemory);

		DataGroup dummy = DataGroup.withNameInData("dummy");
		recordsInMemory.create("metadataCollectionVariable", "dummy1", dummy,
				DataGroup.withNameInData("collectedLinksList"), "cora");
		recordsInMemory.create("metadataItemCollection", "dummy1", dummy,
				DataGroup.withNameInData("collectedLinksList"), "cora");
		recordsInMemory.create("metadataCollectionItem", "dummy1", dummy,
				DataGroup.withNameInData("collectedLinksList"), "cora");
		recordsInMemory.create("metadataTextVariable", "dummy1", dummy,
				DataGroup.withNameInData("collectedLinksList"), "cora");
		recordsInMemory.create("metadataRecordLink", "dummy1", dummy,
				DataGroup.withNameInData("collectedLinksList"), "cora");
		return recordsInMemory;
	}

	private static void addPlace(RecordStorageInMemory recordsInMemory) {
		DataGroup recordInfo = DataGroup.withNameInData("recordInfo");
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("type", "place"));
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("id", "place:0001"));

		/**
		 * <pre>
		 * 		recordInfo
		 * 			type
		 * 			id
		 * 			organisation
		 * 			user
		 * 			tsCreated (recordCreatedDate)
		 * 			list tsUpdated (recordUpdatedDate)
		 * 			catalog Language
		 * </pre>
		 */

		DataGroup dataGroup = DataGroup.withNameInData("authority");
		dataGroup.addChild(recordInfo);
		recordsInMemory.create("place", "place:0001", dataGroup,
				DataGroup.withNameInData("collectedLinksList"), "cora");
	}

	private static void addSecondPlace(RecordStorage recordsInMemory) {
		DataGroup recordInfo = DataGroup.withNameInData("recordInfo");
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("type", "place"));
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("id", "place:0002"));

		DataGroup dataGroup = DataGroup.withNameInData("authority");
		dataGroup.addChild(recordInfo);

		// create link to place:0001
		DataGroup collectedLinksList = DataGroup.withNameInData("collectedLinksList");
		DataGroup recordToRecordLink = DataGroup.withNameInData("recordToRecordLink");

		DataGroup from = DataGroup.withNameInData("from");
		DataAtomic fromLinkedRecordType = DataAtomic.withNameInDataAndValue("linkedRecordType",
				"place");
		from.addChild(fromLinkedRecordType);

		DataAtomic fromLinkedRecordId = DataAtomic.withNameInDataAndValue("linkedRecordId",
				"place:0002");
		from.addChild(fromLinkedRecordId);
		recordToRecordLink.addChild(from);

		DataGroup to = DataGroup.withNameInData("to");
		DataAtomic toLinkedRecordType = DataAtomic.withNameInDataAndValue("linkedRecordType",
				"place");
		to.addChild(toLinkedRecordType);

		DataAtomic toLinkedRecordId = DataAtomic.withNameInDataAndValue("linkedRecordId",
				"place:0001");
		to.addChild(toLinkedRecordId);
		recordToRecordLink.addChild(to);

		collectedLinksList.addChild(recordToRecordLink);
		recordsInMemory.create("place", "place:0002", dataGroup, collectedLinksList, "cora");
	}

	private static void addMetadata(RecordStorageInMemory recordsInMemory) {
		String metadata = "metadataGroup";
		DataGroup dataGroup = DataGroup.withNameInData("metadata");

		DataGroup recordInfo = DataGroup.withNameInData("recordInfo");
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("id", "place"));
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("type", metadata));
		dataGroup.addChild(recordInfo);
		recordsInMemory.create(metadata, "place", dataGroup,
				DataGroup.withNameInData("collectedLinksList"), "cora");
	}

	private static void addPresentation(RecordStorageInMemory recordsInMemory) {
		String presentation = "presentation";
		DataGroup dataGroup = DataGroup.withNameInData(presentation);

		DataGroup recordInfo = DataGroup.withNameInData("recordInfo");
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("id", "placeView"));
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("type", presentation));
		dataGroup.addChild(recordInfo);

		recordsInMemory.create(presentation, "placeView", dataGroup,
				DataGroup.withNameInData("collectedLinksList"), "cora");
	}

	private static void addText(RecordStorageInMemory recordsInMemory) {
		String text = "text";
		DataGroup dataGroup = DataGroup.withNameInData("text");

		DataGroup recordInfo = DataGroup.withNameInData("recordInfo");
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("id", "placeText"));
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("type", text));
		dataGroup.addChild(recordInfo);
		recordsInMemory.create(text, "placeText", dataGroup,
				DataGroup.withNameInData("collectedLinksList"), "cora");
	}

	private static void addRecordType(RecordStorageInMemory recordsInMemory) {
		String recordType = "recordType";
		DataGroup dataGroup = DataGroup.withNameInData(recordType);

		DataGroup recordInfo = DataGroup.withNameInData("recordInfo");
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("id", "metadata"));
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("type", recordType));
		dataGroup.addChild(recordInfo);

		dataGroup.addChild(DataAtomic.withNameInDataAndValue("abstract", "false"));
		recordsInMemory.create(recordType, "metadata", dataGroup,
				DataGroup.withNameInData("collectedLinksList"), "cora");
	}

	private static void addRecordTypeRecordType(RecordStorageInMemory recordsInMemory) {
		String recordType = "recordType";
		DataGroup dataGroup = DataGroup.withNameInData(recordType);

		DataGroup recordInfo = DataGroup.withNameInData("recordInfo");
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("id", "recordType"));
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("type", recordType));
		dataGroup.addChild(recordInfo);

		dataGroup.addChild(DataAtomic.withNameInDataAndValue("id", "recordType"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("metadataId", "recordType"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("presentationViewId",
				"presentation:pgRecordTypeView"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("presentationFormId",
				"presentation:pgRecordTypeForm"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("newMetadataId", "recordTypeNew"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("newPresentationFormId",
				"presentation:pgRecordTypeFormNew"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("listPresentationViewId",
				"presentation:pgRecordTypeViewList"));
		dataGroup.addChild(
				DataAtomic.withNameInDataAndValue("searchMetadataId", "metadata:recordTypeSearch"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("searchPresentationFormId",
				"presentation:pgRecordTypeSearchForm"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("userSuppliedId", "true"));
		dataGroup.addChild(
				DataAtomic.withNameInDataAndValue("permissionKey", "RECORDTYPE_RECORDTYPE"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("selfPresentationViewId",
				"presentation:pgrecordTypeRecordType"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("abstract", "false"));
		recordsInMemory.create(recordType, "recordType", dataGroup,
				DataGroup.withNameInData("collectedLinksList"), "cora");
	}

	private static void addRecordTypeRecordTypeAutoGeneratedId(
			RecordStorageInMemory recordsInMemory) {
		String recordType = "recordType";
		DataGroup dataGroup = DataGroup.withNameInData(recordType);

		DataGroup recordInfo = DataGroup.withNameInData("recordInfo");
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("id", "recordType"));
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("type", recordType));
		dataGroup.addChild(recordInfo);
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("id", "recordType"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("metadataId", "recordType"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("presentationViewId",
				"presentation:pgRecordTypeView"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("presentationFormId",
				"presentation:pgRecordTypeForm"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("newMetadataId", "recordTypeNew"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("newPresentationFormId",
				"presentation:pgRecordTypeFormNew"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("listPresentationViewId",
				"presentation:pgRecordTypeViewList"));
		dataGroup.addChild(
				DataAtomic.withNameInDataAndValue("searchMetadataId", "metadata:recordTypeSearch"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("searchPresentationFormId",
				"presentation:pgRecordTypeSearchForm"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("userSuppliedId", "false"));
		dataGroup.addChild(
				DataAtomic.withNameInDataAndValue("permissionKey", "RECORDTYPE_RECORDTYPE"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("selfPresentationViewId",
				"presentation:pgrecordTypeRecordType"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("abstract", "false"));
		recordsInMemory.create(recordType, "recordTypeAutoGeneratedId", dataGroup,
				DataGroup.withNameInData("collectedLinksList"), "cora");
	}

	private static void addRecordTypePlace(RecordStorageInMemory recordsInMemory) {
		String recordType = "recordType";
		DataGroup dataGroup = DataGroup.withNameInData(recordType);

		DataGroup recordInfo = DataGroup.withNameInData("recordInfo");
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("id", "place"));
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("type", recordType));
		dataGroup.addChild(recordInfo);

		dataGroup.addChild(DataAtomic.withNameInDataAndValue("id", "place"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("metadataId", "metadata:place"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("presentationViewId",
				"presentation:pgPlaceView"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("presentationFormId",
				"presentation:pgPlaceForm"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("newMetadataId", "metadata:placeNew"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("newPresentationFormId",
				"presentation:pgPlaceFormNew"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("listPresentationViewId",
				"presentation:pgPlaceViewList"));
		dataGroup.addChild(
				DataAtomic.withNameInDataAndValue("searchMetadataId", "metadata:placeSearch"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("searchPresentationFormId",
				"presentation:pgPlaceSearchForm"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("userSuppliedId", "false"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("permissionKey", "RECORDTYPE_PLACE"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("selfPresentationViewId",
				"presentation:pgPlaceRecordType"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("abstract", "false"));
		recordsInMemory.create(recordType, "place", dataGroup,
				DataGroup.withNameInData("collectedLinksList"), "cora");
	}

	private static void addRecordTypeBadType(RecordStorageInMemory recordsInMemory) {
		String recordType = "recordType";
		DataGroup dataGroup = DataGroup.withNameInData(recordType);

		DataGroup recordInfo = DataGroup.withNameInData("recordInfo");
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("id", "place"));
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("type", recordType));
		dataGroup.addChild(recordInfo);

		dataGroup.addChild(DataAtomic.withNameInDataAndValue("id", "place"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("metadataId", "metadata:place"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("presentationViewId",
				"presentation:pgPlaceView"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("presentationFormId",
				"presentation:pgPlaceForm"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("newMetadataId", "metadata:placeNew"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("newPresentationFormId",
				"presentation:pgPlaceFormNew"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("listPresentationViewId",
				"presentation:pgPlaceViewList"));
		dataGroup.addChild(
				DataAtomic.withNameInDataAndValue("searchMetadataId", "metadata:placeSearch"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("searchPresentationFormId",
				"presentation:pgPlaceSearchForm"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("userSuppliedId", "false"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("permissionKey", "RECORDTYPE_PLACE"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("selfPresentationViewId",
				"presentation:pgPlaceRecordType"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("abstract", "false"));
		recordsInMemory.create(recordType, "place&& &&\\\\", dataGroup,
				DataGroup.withNameInData("collectedLinksList"), "cora");
	}

	private static void addAbstractRecordTypes(RecordStorageInMemory recordsInMemory) {
		String recordType = "recordType";
		DataGroup dataGroup = DataGroup.withNameInData(recordType);

		DataGroup recordInfo = DataGroup.withNameInData("recordInfo");
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("id", "abstract"));
		recordInfo.addChild(DataAtomic.withNameInDataAndValue("type", recordType));
		dataGroup.addChild(recordInfo);

		dataGroup.addChild(DataAtomic.withNameInDataAndValue("metadataId", "abstract"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("presentationViewId",
				"presentation:pgAbstractView"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("presentationFormId",
				"presentation:pgAbstractForm"));
		dataGroup.addChild(
				DataAtomic.withNameInDataAndValue("newMetadataId", "metadata:abstractNew"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("newPresentationFormId",
				"presentation:pgAbstractFormNew"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("listPresentationViewId",
				"presentation:pgAbstractViewList"));
		dataGroup.addChild(
				DataAtomic.withNameInDataAndValue("searchMetadataId", "metadata:AbstractSearch"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("searchPresentationFormId",
				"presentation:pgAbstractSearchForm"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("userSuppliedId", "false"));
		dataGroup.addChild(
				DataAtomic.withNameInDataAndValue("permissionKey", "RECORDTYPE_ABSTRACT"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("selfPresentationViewId",
				"presentation:pgAbstractRecordType"));
		dataGroup.addChild(DataAtomic.withNameInDataAndValue("abstract", "true"));
		// records.get(recordType).put("abstract", dataGroup);
		recordsInMemory.create(recordType, "abstract", dataGroup,
				DataGroup.withNameInData("collectedLinksList"), "cora");
		DataGroup dataGroup1 = DataGroup.withNameInData(recordType);

		DataGroup recordInfo1 = DataGroup.withNameInData("recordInfo1");
		recordInfo1.addChild(DataAtomic.withNameInDataAndValue("id", "child1"));
		recordInfo1.addChild(DataAtomic.withNameInDataAndValue("type", recordType));
		dataGroup1.addChild(recordInfo1);

		dataGroup1.addChild(DataAtomic.withNameInDataAndValue("metadataId", "child1"));
		dataGroup1.addChild(DataAtomic.withNameInDataAndValue("presentationViewId",
				"presentation:pgChild1View"));
		dataGroup1.addChild(DataAtomic.withNameInDataAndValue("presentationFormId",
				"presentation:pgChild1Form"));
		dataGroup1
				.addChild(DataAtomic.withNameInDataAndValue("newMetadataId", "metadata:child1New"));
		dataGroup1.addChild(DataAtomic.withNameInDataAndValue("newPresentationFormId",
				"presentation:pgChild1FormNew"));
		dataGroup1.addChild(DataAtomic.withNameInDataAndValue("listPresentationViewId",
				"presentation:pgChild1ViewList"));
		dataGroup1.addChild(
				DataAtomic.withNameInDataAndValue("searchMetadataId", "metadata:Child1Search"));
		dataGroup1.addChild(DataAtomic.withNameInDataAndValue("searchPresentationFormId",
				"presentation:pgChild1SearchForm"));
		dataGroup1.addChild(DataAtomic.withNameInDataAndValue("userSuppliedId", "true"));
		dataGroup1
				.addChild(DataAtomic.withNameInDataAndValue("permissionKey", "RECORDTYPE_CHILD1"));
		dataGroup1.addChild(DataAtomic.withNameInDataAndValue("selfPresentationViewId",
				"presentation:pgChild1RecordType"));

		dataGroup1.addChild(DataAtomic.withNameInDataAndValue("abstract", "false"));
		dataGroup1.addChild(DataAtomic.withNameInDataAndValue("parentId", "abstract"));
		// records.get(recordType).put("child1", dataGroup1);
		recordsInMemory.create(recordType, "child1", dataGroup1,
				DataGroup.withNameInData("collectedLinksList"), "cora");
		DataGroup dataGroup2 = DataGroup.withNameInData(recordType);

		DataGroup recordInfo2 = DataGroup.withNameInData("recordInfo2");
		recordInfo2.addChild(DataAtomic.withNameInDataAndValue("id", "child2"));
		recordInfo2.addChild(DataAtomic.withNameInDataAndValue("type", recordType));
		dataGroup2.addChild(recordInfo2);

		dataGroup2.addChild(DataAtomic.withNameInDataAndValue("metadataId", "child2"));
		dataGroup2.addChild(DataAtomic.withNameInDataAndValue("presentationViewId",
				"presentation:pgChild2View"));
		dataGroup2.addChild(DataAtomic.withNameInDataAndValue("presentationFormId",
				"presentation:pgChild2Form"));
		dataGroup2
				.addChild(DataAtomic.withNameInDataAndValue("newMetadataId", "metadata:child2New"));
		dataGroup2.addChild(DataAtomic.withNameInDataAndValue("newPresentationFormId",
				"presentation:pgChild2FormNew"));
		dataGroup2.addChild(DataAtomic.withNameInDataAndValue("listPresentationViewId",
				"presentation:pgChild2ViewList"));
		dataGroup2.addChild(
				DataAtomic.withNameInDataAndValue("searchMetadataId", "metadata:Child2Search"));
		dataGroup2.addChild(DataAtomic.withNameInDataAndValue("searchPresentationFormId",
				"presentation:pgChild2SearchForm"));
		dataGroup2.addChild(DataAtomic.withNameInDataAndValue("userSuppliedId", "true"));
		dataGroup2
				.addChild(DataAtomic.withNameInDataAndValue("permissionKey", "RECORDTYPE_CHILD2"));
		dataGroup2.addChild(DataAtomic.withNameInDataAndValue("selfPresentationViewId",
				"presentation:pgChild2RecordType"));

		dataGroup2.addChild(DataAtomic.withNameInDataAndValue("abstract", "false"));
		dataGroup2.addChild(DataAtomic.withNameInDataAndValue("parentId", "abstract"));
		// records.get(recordType).put("child2", dataGroup2);
		recordsInMemory.create(recordType, "child2", dataGroup2,
				DataGroup.withNameInData("collectedLinksList"), "cora");
	}
}

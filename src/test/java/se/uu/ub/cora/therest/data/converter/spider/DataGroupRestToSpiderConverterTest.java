package se.uu.ub.cora.therest.data.converter.spider;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.spider.data.SpiderDataAtomic;
import se.uu.ub.cora.spider.data.SpiderDataGroup;
import se.uu.ub.cora.spider.data.SpiderDataRecordLink;
import se.uu.ub.cora.therest.data.RestDataAtomic;
import se.uu.ub.cora.therest.data.RestDataAttribute;
import se.uu.ub.cora.therest.data.RestDataGroup;
import se.uu.ub.cora.therest.data.RestDataRecordLink;
import se.uu.ub.cora.therest.data.converter.ConverterException;

public class DataGroupRestToSpiderConverterTest {
	private RestDataGroup restDataGroup;
	private DataGroupRestToSpiderConverter dataGroupRestToSpiderConverter;

	@BeforeMethod
	public void setUp() {
		restDataGroup = RestDataGroup.withNameInData("nameInData");
		dataGroupRestToSpiderConverter = DataGroupRestToSpiderConverter
				.fromRestDataGroup(restDataGroup);
	}

	@Test
	public void testToSpider() {
		SpiderDataGroup spiderDataGroup = dataGroupRestToSpiderConverter.toSpider();
		assertEquals(spiderDataGroup.getNameInData(), "nameInData");
	}

	@Test
	public void testToSpiderWithAttribute() {
		restDataGroup.addAttributeByIdWithValue("attributeId", "attributeValue");

		SpiderDataGroup spiderDataGroup = dataGroupRestToSpiderConverter.toSpider();

		String attributeValue = spiderDataGroup.getAttributes().get("attributeId");
		assertEquals(attributeValue, "attributeValue");
	}

	@Test
	public void testToSpiderWithAtomicChild() {
		restDataGroup.addChild(RestDataAtomic.withNameInDataAndValue("atomicId", "atomicValue"));

		SpiderDataGroup spiderDataGroup = dataGroupRestToSpiderConverter.toSpider();

		SpiderDataAtomic atomicChild = (SpiderDataAtomic) spiderDataGroup.getChildren().get(0);
		assertEquals(atomicChild.getNameInData(), "atomicId");
		assertEquals(atomicChild.getValue(), "atomicValue");
	}

	@Test
	public void testToSpiderWithRecordLinkChild() {
		RestDataRecordLink restDataRecordLink = RestDataRecordLink
				.withNameInDataAndRecordTypeAndRecordId("aLink", "someRecordType", "someRecordId");
		restDataGroup.addChild(restDataRecordLink);

		SpiderDataGroup spiderDataGroup = dataGroupRestToSpiderConverter.toSpider();

		SpiderDataRecordLink spiderDataRecordLink = (SpiderDataRecordLink) spiderDataGroup
				.getFirstChildWithNameInData("aLink");
		assertEquals(spiderDataRecordLink.getNameInData(), "aLink");
		assertEquals(spiderDataRecordLink.getRecordType(), "someRecordType");
		assertEquals(spiderDataRecordLink.getRecordId(), "someRecordId");
	}

	@Test
	public void testToSpiderWithGroupChild() {
		restDataGroup.addChild(RestDataGroup.withNameInData("groupId"));

		SpiderDataGroup spiderDataGroup = dataGroupRestToSpiderConverter.toSpider();

		SpiderDataGroup groupChild = (SpiderDataGroup) spiderDataGroup.getChildren().get(0);
		assertEquals(groupChild.getNameInData(), "groupId");
	}

	@Test
	public void testToSpiderWithGroupChildWithAtomicChild() {
		RestDataGroup restGroupChild = RestDataGroup.withNameInData("groupId");
		restGroupChild.addChild(RestDataAtomic.withNameInDataAndValue("grandChildAtomicId",
				"grandChildAtomicValue"));
		restGroupChild.addAttributeByIdWithValue("groupChildAttributeId",
				"groupChildAttributeValue");
		restDataGroup.addChild(restGroupChild);

		SpiderDataGroup spiderDataGroup = dataGroupRestToSpiderConverter.toSpider();

		SpiderDataGroup groupChild = (SpiderDataGroup) spiderDataGroup.getChildren().get(0);
		SpiderDataAtomic grandChildAtomic = (SpiderDataAtomic) groupChild.getChildren().get(0);
		assertEquals(grandChildAtomic.getNameInData(), "grandChildAtomicId");
		String groupChildAttributeValue = restGroupChild.getAttributes()
				.get("groupChildAttributeId");
		assertEquals(groupChildAttributeValue, "groupChildAttributeValue");
	}

	@Test(expectedExceptions = ConverterException.class)
	public void testToSpiderWithAttributeAsChild() {
		restDataGroup.addChild(RestDataAttribute.withNameInDataAndValue("atomicId", "atomicValue"));

		SpiderDataGroup spiderDataGroup = dataGroupRestToSpiderConverter.toSpider();

		SpiderDataAtomic atomicChild = (SpiderDataAtomic) spiderDataGroup.getChildren().get(0);
		assertEquals(atomicChild.getNameInData(), "atomicId");
		assertEquals(atomicChild.getValue(), "atomicValue");
	}
}
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

package se.uu.ub.cora.therest.data;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import se.uu.ub.cora.spider.data.DataMissingException;

import static org.testng.Assert.*;

public class RestDataGroupTest {
	private RestDataGroup restDataGroup;

	@BeforeMethod
	public void setUp() {
		restDataGroup = RestDataGroup.withNameInData("nameInData");
	}

	@Test
	public void testGroupIsRestData() {
		assertTrue(restDataGroup instanceof RestData);
	}

	@Test
	public void testInit() {
		assertEquals(restDataGroup.getNameInData(), "nameInData",
				"NameInData shold be the one set in the constructor");

		assertNotNull(restDataGroup.getAttributes(),
				"Attributes should not be null for a new DataGroup");

		restDataGroup.addAttributeByIdWithValue("nameInData", "Value");

		assertEquals(restDataGroup.getAttributes().get("nameInData"), "Value",
				"Attribute with nameInData nameInData should have value Value");

		assertNotNull(restDataGroup.getChildren(),
				"Children should not be null for a new DataGroup");

		RestDataElement restDataElement = RestDataGroup.withNameInData("nameInData2");
		restDataGroup.addChild(restDataElement);
		assertEquals(restDataGroup.getChildren().stream().findAny().get(), restDataElement,
				"Child should be the same as the one we added");

	}

	@Test
	public void testInitWithRepeatId() {
		restDataGroup.setRepeatId("x1");
		assertEquals(restDataGroup.getRepeatId(), "x1");
	}

	@Test
	public void testContainsChildWithNameInData(){
		RestDataElement restDataElement = RestDataGroup.withNameInData("nameInData2");
		restDataGroup.addChild(restDataElement);
		assertTrue(restDataGroup.containsChildWithNameInData("nameInData2"));
	}

	@Test
	public void testContainsChildWithNameInDataNotFound(){
		RestDataElement restDataElement = RestDataGroup.withNameInData("nameInData2");
		restDataGroup.addChild(restDataElement);
		assertFalse(restDataGroup.containsChildWithNameInData("nameInData_NOT_FOUND"));
	}

	@Test
	public void testGetFirstChildWithNameInData(){
		RestDataElement restDataElement = RestDataGroup.withNameInData("nameInData2");
		restDataGroup.addChild(restDataElement);
		assertNotNull(restDataGroup.getFirstChildWithNameInData("nameInData2"));
	}

	@Test(expectedExceptions = DataMissingException.class)
	public void testGetFirstChildWithNameInDataNotFound(){
		restDataGroup.getFirstChildWithNameInData("nameInData_NOT_FOUND");
	}
}

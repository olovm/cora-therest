/*
 * Copyright 2015, 2019 Uppsala University Library
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

package se.uu.ub.cora.therest.data.converter.coradata;

import se.uu.ub.cora.data.Data;
import se.uu.ub.cora.data.DataGroup;
import se.uu.ub.cora.data.DataList;
import se.uu.ub.cora.data.DataRecord;
import se.uu.ub.cora.therest.data.RestDataGroup;
import se.uu.ub.cora.therest.data.RestDataList;
import se.uu.ub.cora.therest.data.RestDataRecord;
import se.uu.ub.cora.therest.data.converter.ConverterInfo;

public final class DataListDataToRestConverter {

	private DataList dataList;
	private String baseURL;

	public static DataListDataToRestConverter fromDataListWithBaseURL(
			DataList dataList, String url) {
		return new DataListDataToRestConverter(dataList, url);
	}

	private DataListDataToRestConverter(DataList dataList, String url) {
		this.dataList = dataList;
		this.baseURL = url;
	}

	public RestDataList toRest() {
		RestDataList restDataList = RestDataList
				.withContainDataOfType(dataList.getContainDataOfType());

		restDataList.setTotalNo(dataList.getTotalNumberOfTypeInStorage());
		restDataList.setFromNo(dataList.getFromNo());
		restDataList.setToNo(dataList.getToNo());

		for (Data data : dataList.getDataList()) {
			convertDataToRest(restDataList, data);
		}
		return restDataList;
	}

	private void convertDataToRest(RestDataList restDataList, Data data) {
		if (data instanceof DataRecord) {
			convertDataRecordToRest(restDataList, (DataRecord) data);
		} else {
			convertDataGroupToRest(restDataList, (DataGroup) data);
		}
	}

	private void convertDataRecordToRest(RestDataList restDataList, DataRecord data) {
		DataToRestConverterFactory converterFactory = new DataToRestConverterFactoryImp();
		RestDataRecord restRecord = DataRecordToRestConverter
				.fromDataRecordWithBaseURLAndConverterFactory(data, baseURL,
						converterFactory)
				.toRest();
		restDataList.addData(restRecord);
	}

	private void convertDataGroupToRest(RestDataList restDataList, DataGroup data) {
		ConverterInfo converterInfo = ConverterInfo.withBaseURLAndRecordURLAndTypeAndId(baseURL, "",
				"", "");
		RestDataGroup restGroup = DataGroupDataToRestConverter
				.fromDataGroupWithDataGroupAndConverterInfo(data, converterInfo).toRest();
		restDataList.addData(restGroup);
	}
}

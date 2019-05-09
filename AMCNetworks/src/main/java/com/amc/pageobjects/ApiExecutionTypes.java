package com.amc.pageobjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.amc.baseclass.AMCBaseClass;

public class ApiExecutionTypes extends AMCBaseClass {

	public StringBuffer textView = new StringBuffer();
	public StringBuffer textView_1 = new StringBuffer();
	public StringBuffer textView_2 = new StringBuffer();
	public String jsonPrettyPrintString = null;
	public String text2 = null;
	public String text1 = null;
	public String text = null;
	public static String accessToken = "no value";
	public String token = "no value";
	public JSONObject jsonObj;
	public static JSONObject mpJsonObj;
	public JSONObject jsonObjEmpty = new JSONObject();
	public static JSONObject wopJsonObj;
	public static JSONObject rlJsonObj;
	public String apirequest = "no request";
	public ArrayList<String> obj1_tagname = new ArrayList<String>();
	public ArrayList<String> obj2_tagname = new ArrayList<String>();
	public ArrayList<String> paramvalues = new ArrayList<String>();
	public ArrayList<NameValuePair> postParameters;
	public List<NameValuePair> nameValuePairs;
	public String[] apirequests;
	public String jSonTags;
	public String jSonObj;
	public int testline;
	public String paramText;
	public String paramName;
	public String pName;
	public String excelSheet;
	public static String externalFileName;
	public String path = System.getProperty("user.dir");

	// ==========Method For API Response===========
	public void APIResponse(String sheetname, String rlAssetID) throws ClientProtocolException, IOException {
		workBook = excelWorkBook.get();
		String[][] dataBook = getDataFromExcel(workBook, sheetname);
		String ApiType = null;
		String GetValue = null;
		XSSFSheet sheet = workBook.getSheet(sheetname);
		int rowCount = sheet.getPhysicalNumberOfRows();
		for (int exData = 1; exData <= rowCount; exData++) {
			ApiType = dataBook[exData][1].toString();
			GetValue = dataBook[exData][5].toString();
			System.out.println("ApiType is : " + ApiType);
			System.out.println("GetValue is : " + GetValue);
			if (ApiType.equalsIgnoreCase("Post") && !(GetValue.equalsIgnoreCase("Result"))) {
				testhttpclientforPost(exData, sheetname,rlAssetID);
				readJson(exData, GetValue);
			} else if (ApiType.equalsIgnoreCase("GET") && GetValue.equalsIgnoreCase("Result")) {
				getapiExecute(String.valueOf(exData), sheetname,rlAssetID);
				break;
			} else if (ApiType.equalsIgnoreCase("Post") && GetValue.equalsIgnoreCase("Result")) {
				testhttpclientforPost(exData, sheetname,rlAssetID);
				break;
			}
		}

	}

	// ========Method For API Get Call==============
	public JSONObject getapiExecute(String lineNumbers, String sheetname, String rlAssetID) throws ClientProtocolException, IOException {
		String RLAssetID = rlAssetID;
		textView.delete(0, textView.length());
		textView_1.delete(0, textView_1.length());
		// clearJSonObj();
		String[] cTypes;
		String[] hTypes;
		logStepMessage("Started executing API Testing");
		String[] lines;
		if (lineNumbers.contains(",")) {
			lines = lineNumbers.split(",");
		} else {
			lines = lineNumbers.split(" ");
		}

		for (int lin = 1; lin <= lines.length; lin++) {
			int lineNumber = Integer.parseInt(lines[lin - 1]);
			testline = lineNumber;
			workBook = excelWorkBook.get();
			String[][] dataBook = getDataFromExcel(workBook, sheetname);
			logStepMessage("Verify Api Type is Get  or Post");
			if (dataBook[lineNumber][1].toString().equalsIgnoreCase("GET")) {
				logStepMessage("Given url is Get API continue execution");
				HttpClient client = HttpClients.createDefault();
				logStepMessage("Given API is : " + dataBook[lineNumber][0]);
				HttpGet request = new HttpGet(dataBook[lineNumber][0]);
				String cType = dataBook[lineNumber][2].toString();
				String hType = dataBook[lineNumber][3].toString().replace("%s", RLAssetID);

				if (cType.contains(",")) {
					cTypes = cType.split(",");
					hTypes = hType.split(",");
				} else {
					cTypes = cType.split(" ");
					hTypes = hType.split(" ");
				}
				for (int i = 0; i <= cTypes.length - 1; i++) {
					if (cTypes[i].trim().contains("Authorization")) {

						logStepMessage("Using Access tokena as Bearer : " + accessToken);
						logStepMessage("Headers are: : " + cTypes[i] + "------" + hTypes[i] + " " + accessToken);
						request.addHeader(cTypes[i], hTypes[i] + " " + accessToken);
					} else if (cTypes[i].trim().contains("auth2")) {
						logStepMessage("Using Access tokena as Bearer : " + token);
						logStepMessage("Headers are: : " + cTypes[i] + "------" + hTypes[i] + " " + token);
						request.addHeader(cTypes[i], hTypes[i] + " " + token);
					} else {
						logStepMessage("Headers are: : " + cTypes[i] + "------" + hTypes[i]);
						request.addHeader(cTypes[i], hTypes[i]);
					}
					System.out.println("Headers are: : " + cTypes[i] + "------" + hTypes[i]);
				}

				HttpResponse response = client.execute(request);
				logStepMessage("Execution Completed and reading response now");
				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				//
				logStepMessage("Verify Status code in response");
				if (response.getStatusLine().getStatusCode() != 200) {

					RuntimeException error = new RuntimeException(
							"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
					System.out.println(error);
					logStepMessage(error.toString());
					throw new RuntimeException(
							"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				} else {
					String line = "";
					while ((line = rd.readLine()) != null) {
						textView_1.append(line);

					}
					if (textView_1.toString().contains("xml")) {
						text1 = convertxmltoJson(textView_1.toString());
					} else if (!textView_1.toString().contains("xml")) {
						jsonObj = new JSONObject();
						text1 = textView_1.toString();
						if (text1.startsWith("[")) {
							textView_1.deleteCharAt(0);
							textView_1.deleteCharAt(textView_1.length() - 1);
							text1 = textView_1.toString();
						}
						System.out.println("response  is : =========" + text1.toString());
					}
					jsonObj = new JSONObject(text1.toString());
					//System.out.println(jsonObj.toString());
					if (sheetname.contains("MP")) {
						mpJsonObj = jsonObj;
					} else if (sheetname.contains("WOP")) {
						wopJsonObj = jsonObj;
					} else if (sheetname.contains("RL")) {
						rlJsonObj = jsonObj;

					}
				}

			}
		}
		return jsonObj;
	}

	// =========Method For Clearing the JSon Object=======
	public void clearJSon() {
		jsonObj = jsonObjEmpty;
		textView_1 = textView_1.delete(0, textView_1.length());
		paramvalues.clear();
	}

	// =========Method For Clearing the JSon Object=======
	public void clearJSonObj() {
		mpJsonObj = jsonObjEmpty;
		wopJsonObj = jsonObjEmpty;

	}

	// ========Method For API Post Call===========
	public JSONObject testhttpclientforPost(int lineNumber, String sheetname, String rlAssetID) throws IOException {
		String RLAssetID = rlAssetID;
		workBook = excelWorkBook.get();
		String[][] dataBook = getDataFromExcel(workBook, sheetname);
		clearJSon();
		// clearJSonObj();
		logStepMessage("Get the details from Excel");
		String reqExcel = dataBook[lineNumber][4].toString();
		addparamvaluesinRequest(lineNumber, dataBook[lineNumber][4].toString(), sheetname,RLAssetID);
		logStepMessage("Http Client service is invoked");
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(dataBook[lineNumber][0].toString());
		String[] cTypes;
		String[] hTypes;
		try {
			if (apirequest.equals("no request")) {
				apirequest = dataBook[lineNumber][4].toString();
				logStepMessage("apirequest is  : --" + apirequest);
			} else if (apirequest.contains("&") && !reqExcel.equalsIgnoreCase("externalFile")) {
				logStepMessage("apirequest is  : --" + apirequest);
				nameValuePairs = new ArrayList<NameValuePair>();
				postParameters = new ArrayList<NameValuePair>();
				String[] params;
				apirequests = apirequest.split("&");
				for (String param : apirequests) {
					if (param.contains("=")) {
						params = param.split("=");

						nameValuePairs.add(new BasicNameValuePair(params[0], params[1]));
						postParameters.add(new BasicNameValuePair(params[0], params[1]));

					}
				}
				logStepMessage("Executing request with http Post method");
				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} else {
				logStepMessage("apirequest is  : --" + apirequest);
				StringEntity entity = new StringEntity(apirequest);
				post.setEntity(entity);
				String cType = dataBook[lineNumber][2].toString();
				String hType = dataBook[lineNumber][3].toString();

				if (cType.contains(",")) {
					cTypes = cType.split(",");
					hTypes = hType.split(",");
				} else {
					cTypes = cType.split(" ");
					hTypes = hType.split(" ");
				}
				for (int i = 0; i <= cTypes.length - 1; i++) {
					logStepMessage("Using Access tokena as Bearer : " + token);
					if (cTypes[i].trim().contains("Authorization")) {
						logStepMessage("Using Access tokena as Bearer : " + accessToken);
						logStepMessage("Headers are: : " + cTypes[i] + "------" + hTypes[i] + " " + accessToken);
						System.out.println(cTypes[i] + "," + hTypes[i] + "-- " + accessToken);
						post.addHeader(cTypes[i], hTypes[i] + " " + accessToken);
					} else if (cTypes[i].trim().contains("auth2")) {
						logStepMessage("Using Access tokena as Bearer : " + token);
						logStepMessage("Headers are: : " + cTypes[i] + "------" + hTypes[i] + " " + token);
						post.addHeader(cTypes[i], hTypes[i] + " " + token);
					} else {
						logStepMessage("Headers are: : " + cTypes[i] + "------" + hTypes[i]);
						post.addHeader(cTypes[i], hTypes[i]);
					}
				}
				logStepMessage("Executing post API with Http Post method");
			}
			HttpResponse response = client.execute(post);
			logStepMessage("Response is : " + response);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			if (response.getStatusLine().getStatusCode() != 200) {
				RuntimeException error = new RuntimeException(
						"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				System.out.println(error);
				logStepMessage(error.toString());
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			} else {
				logStepMessage("API Executed Successully : ");
				String line = "";
				while ((line = rd.readLine()) != null) {

					textView_1.append(line);

				}
				if (textView_1.toString().contains("xml")) {
					text1 = convertxmltoJson(textView_1.toString());
				} else if (!textView_1.toString().contains("xml")) {
					jsonObj = new JSONObject();
					text1 = textView_1.toString();
					if (text1.startsWith("[")) {
						textView_1.deleteCharAt(0);
						textView_1.deleteCharAt(textView_1.length() - 1);
						text1 = textView_1.toString();
					}
					System.out.println("response  is : =========" + textView_1.toString());
				}
				jsonObj = new JSONObject(text1);

				//System.out.println(jsonObj.toString());
				if (sheetname.contains("MP")) {
					mpJsonObj = jsonObj;
				} else if (sheetname.contains("WOP")) {
					wopJsonObj = jsonObj;
				}
			}

			logStepMessage("API Executed Successfully");
			System.out.println("Api Executed successfully");
			logStepMessage(jsonObj.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonObj;
	}

	// =======Method To Convert XML response to JSON=========
	public String convertxmltoJson(String xmlString) {
		try {
			JSONObject xmlJSONObj = XML.toJSONObject(xmlString);
			jsonPrettyPrintString = xmlJSONObj.toString();
			//System.out.println(jsonPrettyPrintString);
		} catch (JSONException je) {
			System.out.println(je.toString());
		}
		return jsonPrettyPrintString;
	}

	public void readFile(String path) throws IOException {
		File file = new File(path);
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		}

		apirequest = convertxmltoJson(sb.toString());

	}

	// ========Method To Add Parameter Values in Request Of Next API=========
	public void addparamvaluesinRequest(int lineNumber, String jsonValue, String sheetName,String rlAssetID) throws IOException {
		workBook = excelWorkBook.get();
		String[][] dataBook = getDataFromExcel(workBook, sheetName);
		apirequest = dataBook[lineNumber][4].toString().replaceAll("%s", rlAssetID);
		if (apirequest.equalsIgnoreCase("externalFile")) {
			if (externalFileName.contains("Media_Asset")) {
				readFile(path + "\\Media_Asset.xml");
			} else if (externalFileName.contains("Component_Data")) {
				readFile(path + "\\Component_Data.xml");
			} else {
				System.out.println("Api Request from Excel");

				logStepMessage("Check request details from Excel");
				try {
					//JSONParser parser = new JSONParser();
					JSONObject jObject = new JSONObject(apirequest);
					logStepMessage("Request in Json formate");
					JSONObject maintag = (JSONObject) jObject.getJSONObject("request");
					//JSONObject metadata = null;
					String[] metaValues;
					if (jsonValue.contains(",")) {
						metaValues = jsonValue.split(",");
					} else {
						metaValues = jsonValue.split(" ");
					}
					logStepMessage("Try ing to add values to request");
					for (int i = 0; i <= metaValues.length - 1; i++) {
						if (accessToken.equals("no value") || token.equals("no value")) {
							System.out.println("No Token values are present");
						} else {
							if (jsonValue.contains(metaValues[i])) {
								//metadata = maintag.getJSONObject("metadata").put(metaValues[i], accessToken);
								maintag.getJSONObject("metadata").put(metaValues[i], accessToken);
							} else if (jsonValue.contains("token")) {
								//metadata = maintag.getJSONObject("metadata").put(metaValues[i], token);
								maintag.getJSONObject("metadata").put(metaValues[i], token);
							}
						}
					}

					apirequest = jObject.toString();
					logStepMessage("Edited request is : ----" + apirequest);
				} catch (Exception e) {
					logStepMessage("request is not in JSON formate : --" + apirequest);

				}
			}
		}
		System.out.println("metadata -- :" + apirequest);
	}

	// ========Method For Reading JSON response=========
	public void readJson(int lineNumber, String getValue) {
		if (getValue.equalsIgnoreCase("access_token")) {
			accessToken = (String) jsonObj.get("access_token");
			logStepMessage(getValue + " value is :" + accessToken);
			System.out.println(getValue + " value is :" + accessToken);
		} else if (getValue.equalsIgnoreCase("token")) {
			token = (String) jsonObj.get("token");
			logStepMessage(getValue + " value is :" + token);
			System.out.println(getValue + " value is :" + token);
		}
	}

	// ======Method For Reading JsonType and API Parameters From XL Sheet========

	public ArrayList<String> getValue(String JsonType, String Param) throws Exception, IOException {
		workBook = excelWorkBook.get();
		String[][] dataBook = getDataFromExcel(workBook, JsonType);
		logStepMessage("Started reading Responses from " + JsonType + " API ");
		//String Objecttype = null;
		String paramvalue;
		String jsontypes = null;
		String[] jTypes;
		clearJSon();
		if (JsonType.equalsIgnoreCase("rl")) {
			jsonObj = rlJsonObj;
		} else if (JsonType.equalsIgnoreCase("mp")) {
			jsonObj = mpJsonObj;
		} else if (JsonType.equalsIgnoreCase("wop")) {
			jsonObj = wopJsonObj;
		}
		XSSFSheet sheet = workBook.getSheet(JsonType);
		int rowCount = sheet.getPhysicalNumberOfRows();
		for (int excellist = 0; excellist <= rowCount; excellist++) {
			paramName = dataBook[excellist][3].toString();
			paramvalue = dataBook[excellist][0].toString();
			if (Param.equalsIgnoreCase(paramName)) {
				logStepMessage("Validating " + Param + " param name in " + JsonType + " API");
				pName = paramName;
				jsontypes = dataBook[excellist][2].toString();
				if (jsontypes.contains(",")) {
					jTypes = jsontypes.split(",");
				} else {
					jTypes = jsontypes.split("  ");
				}
				for (int i = 0; i <= jTypes.length - 1; i++) {
					if (jTypes[i].toString().contains("direct")) {
						paramText = jsonObj.get(paramvalue).toString();

						break;
					} else {

						Object obj_reult = jsonObj.get(jTypes[i].toString());
						//JSONObject Obj_resultTag = null;
						JSONArray Obj_resultArray = null;
						if (obj_reult instanceof JSONObject) {
							jsonObj = (JSONObject) obj_reult;

							//Objecttype = "JsonObject";
						} else {
							Obj_resultArray = (JSONArray) jsonObj.get(jTypes[i].toString());
							// System.out.println("Size of the array :" + Obj_resultArray.length());
							//Objecttype = "JsonArray";
							i = i + 1;
							jsonObj = (JSONObject) Obj_resultArray.get(Integer.parseInt(jTypes[i].toString()));

						}
					}
				}
				paramText = jsonObj.get(paramvalue).toString();
				paramvalues.add(paramText);
				logStepMessage(" Param values are ------ : " + paramvalues);
				System.out.println("Parameter values :" + paramvalues);
				break;

			}
		}

		return paramvalues;

	}

}

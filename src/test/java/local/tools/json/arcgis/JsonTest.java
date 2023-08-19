/**
 * MIT License
 *
 * Copyright (c) 2023 Alexander Perfilev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package local.tools.json.arcgis;

import local.tools.json.JSONObject;
import local.tools.json.JSONException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class JsonTest {
    public static final int ITERATIONS = 1500;

    @Test
    public void parallel() throws InterruptedException, JSONException {
        final JSONObject one = new JSONObject(source);
        ExecutorService e  = Executors.newFixedThreadPool(100);
        for (int i = 0; i < ITERATIONS; i++) {
            e.submit(() -> new JSONObject(one.toString()));
        }
        e.shutdown();
        assertTrue(e.awaitTermination(5, TimeUnit.MINUTES));
    }

    @Test
    public void linear() throws JSONException {
        final JSONObject one = new JSONObject(source);
        for (int i = 0; i < ITERATIONS; i++) {
            new JSONObject(one.toString());
        }
    }

    @Test
    public void parallelToString() throws InterruptedException, JSONException {
        final JSONObject one = new JSONObject(source);
        ExecutorService e  = Executors.newFixedThreadPool(100);
        for (int i = 0; i < ITERATIONS; i++) {
            e.submit(() -> one.toString());
        }
        e.shutdown();
        assertTrue(e.awaitTermination(5, TimeUnit.MINUTES));
    }

    @Test
    public void linearToString() throws JSONException {
        final JSONObject one = new JSONObject(source);
        for (int i = 0; i < ITERATIONS; i++) {
            one.toString();
        }
    }

    @Test
    public void linearNewObject() throws JSONException {
        for (int i = 0; i < ITERATIONS; i++) {
            new JSONObject(source);
        }
    }

    @Test
    public void parallelNewObject() throws InterruptedException {
        ExecutorService e  = Executors.newFixedThreadPool(100);
        for (int i = 0; i < ITERATIONS; i++) {
            e.submit(() -> new JSONObject(source));
        }
        e.shutdown();
        assertTrue(e.awaitTermination(5, TimeUnit.MINUTES));
    }


    private static final String source = "{\n" +
            "  \"allowedUploadFileTypes\": \"\",\n" +
            "  \"capabilities\": \"Query,Create,Editing,Uploads,Update,Delete\",\n" +
            "  \"clusterName\": \"default\",\n" +
            "  \"configuredState\": \"STARTED\",\n" +
            "  \"datasets\": [],\n" +
            "  \"description\": \"\",\n" +
            "  \"extensions\": [],\n" +
            "  \"instancesPerContainer\": 1,\n" +
            "  \"interceptor\": \"\",\n" +
            "  \"isDefault\": false,\n" +
            "  \"isolationLevel\": \"HIGH\",\n" +
            "  \"jsonProperties\": {\n" +
            "    \"adminServiceInfo\": {\n" +
            "      \"database\": {\n" +
            "        \"datasource\": {\"name\": \"/enterpriseDatabases/AGSDataStore_ds_p2k9o74t\"}\n" +
            "      },\n" +
            "      \"name\": \"User200_48_F_Parks_AGSDataStore\",\n" +
            "      \"status\": \"Started\",\n" +
            "      \"type\": \"FeatureServer\"\n" +
            "    },\n" +
            "    \"allowGeometryUpdates\": true,\n" +
            "    \"capabilities\": \"Query,Create,Editing,Uploads,Update,Delete\",\n" +
            "    \"copyrightText\": \"\",\n" +
            "    \"currentVersion\": 10.3,\n" +
            "    \"description\": \"\",\n" +
            "    \"editorTrackingInfo\": {\n" +
            "      \"allowOthersToDelete\": false,\n" +
            "      \"allowOthersToUpdate\": true,\n" +
            "      \"enableEditorTracking\": false,\n" +
            "      \"enableOwnershipAccessControl\": false\n" +
            "    },\n" +
            "    \"enableZDefaults\": true,\n" +
            "    \"fullExtent\": {\n" +
            "      \"spatialReference\": {\"latestWkid\": 4326, \"wkid\": 4326},\n" +
            "      \"xmax\": -102.06745,\n" +
            "      \"xmin\": -108.97556,\n" +
            "      \"ymax\": 40.78046,\n" +
            "      \"ymin\": 37.02315\n" +
            "    },\n" +
            "    \"hasVersionedData\": false,\n" +
            "    \"initialExtent\": {\n" +
            "      \"spatialReference\": {\"latestWkid\": 4326, \"wkid\": 4326},\n" +
            "      \"xmax\": -101.722044,\n" +
            "      \"xmin\": -109.320965,\n" +
            "      \"ymax\": 40.968326,\n" +
            "      \"ymin\": 36.835285\n" +
            "    },\n" +
            "    \"layerIds\": [\"0\"],\n" +
            "    \"layers\": {\n" +
            "      \"0\": {\n" +
            "        \"adminLayerInfo\": {\n" +
            "          \"geometryField\": {\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 2147483647,\n" +
            "            \"name\": \"shape\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeDistinct\",\n" +
            "            \"sqlTypeCode\": 2001,\n" +
            "            \"type\": \"\",\n" +
            "            \"typeName\": \"st_point\"\n" +
            "          },\n" +
            "          \"syncEnabled\": false,\n" +
            "          \"tableExtent\": {\n" +
            "            \"spatialReference\": {\n" +
            "              \"sdesrid\": 300001,\n" +
            "              \"wkid\": 4326,\n" +
            "              \"wkt\": \"GEOGCS[\\\"GCS_WGS_1984\\\",DATUM[\\\"D_WGS_1984\\\",SPHEROID[\\\"WGS_1984\\\",6378137.0,298.257223563]],PRIMEM[\\\"Greenwich\\\",0.0],UNIT[\\\"Degree\\\",0.0174532925199433]]\"\n" +
            "            },\n" +
            "            \"xmax\": -102.06745,\n" +
            "            \"xmin\": -108.97556,\n" +
            "            \"ymax\": 40.78046,\n" +
            "            \"ymin\": 37.02315\n" +
            "          },\n" +
            "          \"tableName\": \"db_di7rl.hsu_ls865.user200_48_f_parks_a\",\n" +
            "          \"xssTrustedFields\": []\n" +
            "        },\n" +
            "        \"advancedQueryCapabilities\": {\n" +
            "          \"supportsDistinct\": true,\n" +
            "          \"supportsOrderBy\": true,\n" +
            "          \"supportsPagination\": false,\n" +
            "          \"supportsQueryWithDistance\": false,\n" +
            "          \"supportsReturningQueryExtent\": false,\n" +
            "          \"supportsStatistics\": true\n" +
            "        },\n" +
            "        \"allowGeometryUpdates\": true,\n" +
            "        \"capabilities\": \"Query,Create,Editing,Uploads,Update,Delete\",\n" +
            "        \"copyrightText\": \"\",\n" +
            "        \"currentVersion\": 10.3,\n" +
            "        \"defaultVisibility\": true,\n" +
            "        \"description\": \"\",\n" +
            "        \"displayField\": \"primary_name\",\n" +
            "        \"drawingInfo\": {\n" +
            "          \"labelingInfo\": null,\n" +
            "          \"renderer\": {\n" +
            "            \"description\": \"\",\n" +
            "            \"label\": \"\",\n" +
            "            \"symbol\": {\n" +
            "              \"angle\": 0,\n" +
            "              \"color\": [118, 168, 0, 255],\n" +
            "              \"outline\": {\"color\": [0, 0, 0, 255], \"width\": 1},\n" +
            "              \"size\": 4,\n" +
            "              \"style\": \"esriSMSCircle\",\n" +
            "              \"type\": \"esriSMS\",\n" +
            "              \"xoffset\": 0,\n" +
            "              \"yoffset\": 0\n" +
            "            },\n" +
            "            \"type\": \"simple\"\n" +
            "          },\n" +
            "          \"transparency\": 0\n" +
            "        },\n" +
            "        \"editFieldsInfo\": null,\n" +
            "        \"enableZDefaults\": true,\n" +
            "        \"extent\": {\n" +
            "          \"spatialReference\": {\"latestWkid\": 4326, \"wkid\": 4326},\n" +
            "          \"xmax\": -102.06745,\n" +
            "          \"xmin\": -108.97556,\n" +
            "          \"ymax\": 40.78046,\n" +
            "          \"ymin\": 37.02315\n" +
            "        },\n" +
            "        \"fields\": [\n" +
            "          {\n" +
            "            \"adminFieldInfo\": {\"uniqueValueGenerator\": {\"function\": true}},\n" +
            "            \"alias\": \"objectid\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": false,\n" +
            "            \"length\": 10,\n" +
            "            \"name\": \"objectid\",\n" +
            "            \"nullable\": false,\n" +
            "            \"sqlType\": \"sqlTypeInteger\",\n" +
            "            \"sqlTypeCode\": 4,\n" +
            "            \"type\": \"esriFieldTypeOID\",\n" +
            "            \"typeName\": \"int4\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"frs_facility_detail_report_url\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"frs_facility_detail_report_url\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"registry_id\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 38,\n" +
            "            \"name\": \"registry_id\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeNumeric\",\n" +
            "            \"sqlTypeCode\": 2,\n" +
            "            \"type\": \"esriFieldTypeDouble\",\n" +
            "            \"typeName\": \"numeric\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"primary_name\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"primary_name\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"location_address\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"location_address\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"supplemental_location\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"supplemental_location\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"city_name\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"city_name\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"county_name\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"county_name\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"fips_code\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 10,\n" +
            "            \"name\": \"fips_code\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeInteger\",\n" +
            "            \"sqlTypeCode\": 4,\n" +
            "            \"type\": \"esriFieldTypeInteger\",\n" +
            "            \"typeName\": \"int4\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"state_code\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"state_code\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"state_name\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"state_name\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"country_name\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"country_name\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"postal_code\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 10,\n" +
            "            \"name\": \"postal_code\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeInteger\",\n" +
            "            \"sqlTypeCode\": 4,\n" +
            "            \"type\": \"esriFieldTypeInteger\",\n" +
            "            \"typeName\": \"int4\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"federal_facility_code\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"federal_facility_code\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"federal_agency_name\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"federal_agency_name\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"tribal_land_code\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"tribal_land_code\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"tribal_land_name\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"tribal_land_name\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"congressional_dist_num\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 10,\n" +
            "            \"name\": \"congressional_dist_num\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeInteger\",\n" +
            "            \"sqlTypeCode\": 4,\n" +
            "            \"type\": \"esriFieldTypeInteger\",\n" +
            "            \"typeName\": \"int4\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"huc_code\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 10,\n" +
            "            \"name\": \"huc_code\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeInteger\",\n" +
            "            \"sqlTypeCode\": 4,\n" +
            "            \"type\": \"esriFieldTypeInteger\",\n" +
            "            \"typeName\": \"int4\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"epa_region_code\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 10,\n" +
            "            \"name\": \"epa_region_code\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeInteger\",\n" +
            "            \"sqlTypeCode\": 4,\n" +
            "            \"type\": \"esriFieldTypeInteger\",\n" +
            "            \"typeName\": \"int4\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"site_type_name\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"site_type_name\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"location_description\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"location_description\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"create_date\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"create_date\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"update_date\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"update_date\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"us_mexico_border_ind\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"us_mexico_border_ind\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"pgm_sys_acrnms\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"pgm_sys_acrnms\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"interest_types\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"interest_types\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"naics_codes\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"naics_codes\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"naics_code_descriptions\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"naics_code_descriptions\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"sic_codes\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"sic_codes\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"sic_code_descriptions\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"sic_code_descriptions\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"latitude83\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 38,\n" +
            "            \"name\": \"latitude83\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeNumeric\",\n" +
            "            \"sqlTypeCode\": 2,\n" +
            "            \"type\": \"esriFieldTypeDouble\",\n" +
            "            \"typeName\": \"numeric\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"longitude83\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 38,\n" +
            "            \"name\": \"longitude83\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeNumeric\",\n" +
            "            \"sqlTypeCode\": 2,\n" +
            "            \"type\": \"esriFieldTypeDouble\",\n" +
            "            \"typeName\": \"numeric\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"conveyor\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"conveyor\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"collect_desc\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"collect_desc\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"accuracy_value\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 10,\n" +
            "            \"name\": \"accuracy_value\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeInteger\",\n" +
            "            \"sqlTypeCode\": 4,\n" +
            "            \"type\": \"esriFieldTypeInteger\",\n" +
            "            \"typeName\": \"int4\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"ref_point_desc\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"ref_point_desc\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"hdatum_desc\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"hdatum_desc\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"alias\": \"source_desc\",\n" +
            "            \"domain\": null,\n" +
            "            \"editable\": true,\n" +
            "            \"length\": 8000,\n" +
            "            \"name\": \"source_desc\",\n" +
            "            \"nullable\": true,\n" +
            "            \"sqlType\": \"sqlTypeVarchar\",\n" +
            "            \"sqlTypeCode\": 12,\n" +
            "            \"type\": \"esriFieldTypeString\",\n" +
            "            \"typeName\": \"varchar\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"geometryType\": \"esriGeometryPoint\",\n" +
            "        \"globalIdField\": null,\n" +
            "        \"hasAttachments\": false,\n" +
            "        \"hasM\": false,\n" +
            "        \"hasZ\": false,\n" +
            "        \"htmlPopupType\": \"esriServerHTMLPopupTypeAsHTMLText\",\n" +
            "        \"id\": 0,\n" +
            "        \"isDataVersioned\": false,\n" +
            "        \"maxRecordCount\": 1000,\n" +
            "        \"maxScale\": 1000000,\n" +
            "        \"minScale\": 0,\n" +
            "        \"name\": \"User200_48_F_Parks_AGSDataStore\",\n" +
            "        \"objectIdField\": \"objectid\",\n" +
            "        \"ogcGeometryType\": [\"POINT\"],\n" +
            "        \"ownershipBasedAccessControlForFeatures\": null,\n" +
            "        \"relationships\": [],\n" +
            "        \"supportedQueryFormats\": \"JSON\",\n" +
            "        \"supportsAdvancedQueries\": true,\n" +
            "        \"supportsRollbackOnFailureParameter\": true,\n" +
            "        \"supportsStatistics\": true,\n" +
            "        \"syncCanReturnChanges\": false,\n" +
            "        \"templates\": [\n" +
            "          {\n" +
            "            \"description\": \"\",\n" +
            "            \"drawingTool\": \"esriFeatureEditToolPoint\",\n" +
            "            \"name\": \"User200_48_F_Parks_AGSDataStore\",\n" +
            "            \"prototype\": {\n" +
            "              \"attributes\": {\n" +
            "                \"accuracy_value\": null,\n" +
            "                \"city_name\": null,\n" +
            "                \"collect_desc\": null,\n" +
            "                \"congressional_dist_num\": null,\n" +
            "                \"conveyor\": null,\n" +
            "                \"country_name\": null,\n" +
            "                \"county_name\": null,\n" +
            "                \"create_date\": null,\n" +
            "                \"epa_region_code\": null,\n" +
            "                \"federal_agency_name\": null,\n" +
            "                \"federal_facility_code\": null,\n" +
            "                \"fips_code\": null,\n" +
            "                \"frs_facility_detail_report_url\": null,\n" +
            "                \"hdatum_desc\": null,\n" +
            "                \"huc_code\": null,\n" +
            "                \"interest_types\": null,\n" +
            "                \"latitude83\": null,\n" +
            "                \"location_address\": null,\n" +
            "                \"location_description\": null,\n" +
            "                \"longitude83\": null,\n" +
            "                \"naics_code_descriptions\": null,\n" +
            "                \"naics_codes\": null,\n" +
            "                \"pgm_sys_acrnms\": null,\n" +
            "                \"postal_code\": null,\n" +
            "                \"primary_name\": null,\n" +
            "                \"ref_point_desc\": null,\n" +
            "                \"registry_id\": null,\n" +
            "                \"sic_code_descriptions\": null,\n" +
            "                \"sic_codes\": null,\n" +
            "                \"site_type_name\": null,\n" +
            "                \"source_desc\": null,\n" +
            "                \"state_code\": null,\n" +
            "                \"state_name\": null,\n" +
            "                \"supplemental_location\": null,\n" +
            "                \"tribal_land_code\": null,\n" +
            "                \"tribal_land_name\": null,\n" +
            "                \"update_date\": null,\n" +
            "                \"us_mexico_border_ind\": null\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        ],\n" +
            "        \"type\": \"Feature Layer\",\n" +
            "        \"typeIdField\": \"\",\n" +
            "        \"types\": [],\n" +
            "        \"useStandardizedQueries\": true\n" +
            "      }\n" +
            "    },\n" +
            "    \"maxRecordCount\": 1000,\n" +
            "    \"serviceDescription\": \"\",\n" +
            "    \"spatialReference\": {\"latestWkid\": 4326, \"wkid\": 4326},\n" +
            "    \"supportedQueryFormats\": \"JSON\",\n" +
            "    \"supportsDisconnectedEditing\": false,\n" +
            "    \"syncEnabled\": false,\n" +
            "    \"units\": \"esriDecimalDegrees\",\n" +
            "    \"xssPreventionInfo\": {\n" +
            "      \"xssInputRule\": \"rejectInvalid\",\n" +
            "      \"xssPreventionEnabled\": true,\n" +
            "      \"xssPreventionRule\": \"input\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"keepAliveInterval\": 1800,\n" +
            "  \"loadBalancing\": \"ROUND_ROBIN\",\n" +
            "  \"maxIdleTime\": 1800,\n" +
            "  \"maxInstancesPerNode\": 0,\n" +
            "  \"maxStartupTime\": 300,\n" +
            "  \"maxUploadFileSize\": 0,\n" +
            "  \"maxUsageTime\": 600,\n" +
            "  \"maxWaitTime\": 60,\n" +
            "  \"minInstancesPerNode\": 0,\n" +
            "  \"portalProperties\": {\n" +
            "    \"isHosted\": true,\n" +
            "    \"portalItems\": [\n" +
            "      {\"itemID\": \"7211893bc25e47c29dacc2e47c6213f4\", \"type\": \"FeatureServer\"}\n" +
            "    ]\n" +
            "  },\n" +
            "  \"private\": false,\n" +
            "  \"properties\": {\n" +
            "    \"antialiasingMode\": \"None\",\n" +
            "    \"cacheDir\": \"\",\n" +
            "    \"cacheOnDemand\": \"false\",\n" +
            "    \"clientCachingAllowed\": \"true\",\n" +
            "    \"disableIdentifyRelates\": \"false\",\n" +
            "    \"dynamicDataWorkspaces\": \"\",\n" +
            "    \"enableDynamicLayers\": \"false\",\n" +
            "    \"exportTilesAllowed\": \"false\",\n" +
            "    \"filePath\": \"${arcgisinput}\\\\Hosted\\\\User200_48_F_Parks_AGSDataStore.FeatureServer\\\\extracted\\\\v101\\\\aMapName.msd\",\n" +
            "    \"ignoreCache\": \"false\",\n" +
            "    \"isCached\": \"false\",\n" +
            "    \"maxBufferCount\": \"100\",\n" +
            "    \"maxDomainCodeCount\": \"25000\",\n" +
            "    \"maxExportTilesCount\": \"100000\",\n" +
            "    \"maxImageHeight\": \"4096\",\n" +
            "    \"maxImageWidth\": \"4096\",\n" +
            "    \"maxRecordCount\": \"1000\",\n" +
            "    \"maxScale\": \"577790.55428899999\",\n" +
            "    \"minScale\": \"4622324.4343090001\",\n" +
            "    \"outputDir\": \"${arcgisoutput}\",\n" +
            "    \"schemaLockingEnabled\": \"true\",\n" +
            "    \"supportedImageReturnTypes\": \"URL\",\n" +
            "    \"textAntialiasingMode\": \"Force\",\n" +
            "    \"useLocalCacheDir\": \"true\",\n" +
            "    \"virtualOutputDir\": \"/rest/directories/arcgisoutput\"\n" +
            "  },\n" +
            "  \"provider\": \"SDS\",\n" +
            "  \"recycleInterval\": 24,\n" +
            "  \"recycleStartTime\": \"00:00\",\n" +
            "  \"serviceName\": \"User200_48_F_Parks_AGSDataStore\",\n" +
            "  \"type\": \"FeatureServer\"\n" +
            "}";
}

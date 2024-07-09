package life.catchyour.dev.core.compose.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import life.catchyour.dev.core.compose.listComponenets.ItemTextRow


@Composable
@Preview(backgroundColor = 0xFFFFFF)
fun PreviewItemTextRow() {
    Column {
        ItemTextRow(
            header = "Header",
            text = "WORKORDER: ================================ Description: US/ONS/NCD/HP " +
                    "Engage Flex Pro Retail System (3DS21AV)/Optical Drive Burn Failure Type: " +
                    "Break Fix OTC: 05K-Extended Warranty PartnerName: AMS_Hemm_P " +
                    "BookingResourceName: AMS-US-HEMMERSBACH-PS-BF PrimaryIncidentType: " +
                    "Laptop Break Fix-AMS-Computing Device Family: Retail Solutions Core  " +
                    "AGREEMENT: ================================ CoverageWindowVal: STD " +
                    "CoverageWindowUsed: COV_WINDOW5 ResponseTimeValue: NCD RepairTimeValue:  " +
                    " MISC: ================================ BusinessSegment: Computing " +
                    "AccountName: Walgreens TimeZone: (GMT-05:00) Eastern Time (US & Canada)  " +
                    "SUBJECT: ================================ US/ONS/NCD/HP Engage Flex Pro " +
                    "Retail System (3DS21AV)/Optical Drive Burn Failure  SpecificSymptom:  " +
                    "ErrorCodes:  Instructions: Medium Case Created on : 9/26/2023 Log Type :" +
                    " Notes Log Action Type : Inbound Customer call Visible Externally : " +
                    "Yes Optical Drive Burn Failure Sep 27 2023 02:58:15 GMT+0530  " +
                    "Priyanka Kumari HPCC_BGL_AMR EndTS__ -----------------------------" +
                    "-------------------------------------------------------  Log Type :" +
                    " Phone Log Action Type : Inbound Customer call Visible Externally : No " +
                    "PRE_Part_Recommendation_Engine   Recommendation Date & Time: 9/27/2023," +
                    " 2:58:34 AM   Dominant Issue Path: chassis_mechanical,cosmetic_unit_damage," +
                    "liquid_spill   PRE Recommended Part   Agent Recommended Part   Additional " +
                    "feedback:  Sep 27 2023 02:59:04 GMT+0530  Priyanka Kumari HPCC_BGL_AMR " +
                    "EndTS__ ----------------------------------------------------------------" +
                    "--------------------  DESCRIPTION: ================================ " +
                    "Optical Drive Burn Failure  CONTACT: ================================ " +
                    "Name: Michael Kress Account Mobile:  Account Phone: 16312187982 Contact " +
                    "Mobile:  Contact Phone: 17603694615 Primary Email: michael.kress@hp.com" +
                    " Secondary Email:   DELIVERY: ================================ ESDT: " +
                    "2023-09-28T12:00:00Z LSDT: 2023-09-28T21:00:00Z  PARTORDERS: ===========" +
                    "===================== PartOrderNumber: MO-700774592 OrganizationName:  Sh" +
                    "ipmentCondition: null----------  EarliestDateAllPartsAvailable:"
        )
        ItemTextRow(header = "Header", text = "Normal text")
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFF)
fun SneakyPreviewItemTextRow() {
    Column {
        ItemTextRow(
            header = "Header",
            text = """WORKORDER
                |===============================
                |asfasdf
                |asdfasdf
                |asdfasdf
            """.trimMargin()
        )
        ItemTextRow(header = "Header", text = "Normal text")
    }
}

@Composable
@Preview(backgroundColor = 0xFFFFFF)
fun BasicPreviewItemTextRow() {
    Column {
        ItemTextRow(
            header = "Header",
            text = """WORKORDER
            """.trimMargin()
        )
        ItemTextRow(header = "Header", text = "Normal text")
    }
}
package com.Demo.service;
import com.Demo.dto.EmployeeDataDTO;
import com.Demo.dto.OmniCarSystemAccess.AvanserOmniRewardsProgramsDTO;
import com.Demo.dto.OmniCarSystemAccess.RetailerDashBoardOneVoiceDTO;
import com.Demo.dto.OmniCarSystemAccess.TrafficReportOminiStoreDTO;
import com.Demo.dto.OmniCarSystemAccess.WorkPlaceSesimiDTO;
import com.Demo.dto.RetailerAddressDTO;

import com.Demo.repository.EmployeeDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PdfService {

    @Autowired
    private EmployeeDataRepository employeeDataRepository;

    //Method to extract the data from the file
    public void extractAndSaveData(MultipartFile file) throws IOException {

        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();

        EmployeeDataDTO employeeDataDTO = createEmployeeData(text);

        PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
        log.info("acroForm==>{}",acroForm);
        if (acroForm != null) {

            for (String fieldName : acroForm.getFields().stream().map(field -> field.getFullyQualifiedName()).toArray(String[]::new)) {
                if (acroForm.getField(fieldName) instanceof PDCheckBox) {
                    PDCheckBox checkBox = (PDCheckBox) acroForm.getField(fieldName);
                    setCheckboxValue(fieldName, "Yes".equals(checkBox.getValue()));
                }
            }
        }
//        employeeDataRepository.save(employeeData);
    }

    private EmployeeDataDTO createEmployeeData(String text) {
        EmployeeDataDTO employeeDataDTO = new EmployeeDataDTO();
        RetailerAddressDTO retailerAddressDTO = new RetailerAddressDTO();

        String firstNamePattern = "First Name\\s+(\\w+)";
        String lastNamePattern = "Last Name\\s+(\\w+)";
        String mobilePattern = "Mobile\\s+(\\d+)";
        String titlePattern = "Title\\s+([\\w\\s]+)"; // Capture words with spaces
        String emailPattern = "Email Address\\s+(\\w+@\\w+\\.\\w+)";
        String managerEmailPattern = "Manager's Email\\s+(\\w+@\\w+\\.\\w+)";
        String cdsidPattern = "CDSID\\s+([\\w-]+)";
        String dmsidPattern = "DMSID\\s+(\\w+)";
        String retailerPattern = "Retailer\\s+(.+)";
        String cityPattern = "City\\s+-\\s+(\\w+)";
        String statePattern = "State\\s+(\\w+)";
        String postcodePattern = "Postcode\\s+(\\w+)";
        String countryPattern = "Country\\s+(\\w+)";
        String startDatePattern = "Start Date\\s+(.*)";
        String finishDatePattern = "Finish Date\\s+(.*)";

        String[] lines = text.split("\n");
        for (String line : lines) {
            Matcher matcher;

            if (employeeDataDTO.getFirstName() == null) {
                matcher = Pattern.compile(firstNamePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setFirstName(matcher.group(1));
                }
            }

            if (employeeDataDTO.getLastName() == null) {
                matcher = Pattern.compile(lastNamePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setLastName(matcher.group(1));
                }
            }

            if (employeeDataDTO.getMobile() == null) {
                matcher = Pattern.compile(mobilePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setMobile(matcher.group(1));
                }
            }

            if (employeeDataDTO.getTitle() == null) {
                matcher = Pattern.compile(titlePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setTitle(matcher.group(1));
                }
            }

            if (employeeDataDTO.getEmailAddress() == null) {
                matcher = Pattern.compile(emailPattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setEmailAddress(matcher.group(1));
                }
            }

            if (employeeDataDTO.getManagersEmail() == null) {
                matcher = Pattern.compile(managerEmailPattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setManagersEmail(matcher.group(1));
                }
            }

            if (employeeDataDTO.getCDSID() == null) {
                matcher = Pattern.compile(cdsidPattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setCDSID(matcher.group(1));
                }
            }

            if (employeeDataDTO.getDMSID() == null) {
                matcher = Pattern.compile(dmsidPattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setDMSID(matcher.group(1));
                }
            }

            if (employeeDataDTO.getRetailer() == null) {
                matcher = Pattern.compile(retailerPattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setRetailer(matcher.group(1));
                }
            }

            if (employeeDataDTO.getStartDate() == null) {
                matcher = Pattern.compile(startDatePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setStartDate(matcher.group(1));
                }
            }

            if (employeeDataDTO.getFinishDate() == null) {
                matcher = Pattern.compile(finishDatePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setFinishDate(matcher.group(1));
                }
            }

            matcher = Pattern.compile(cityPattern).matcher(line);
            if (matcher.find()) {
                retailerAddressDTO.setCity(matcher.group(1));
            }

            matcher = Pattern.compile(statePattern).matcher(line);
            if (matcher.find()) {
                retailerAddressDTO.setState(matcher.group(1));
            }

            matcher = Pattern.compile(postcodePattern).matcher(line);
            if (matcher.find()) {
                retailerAddressDTO.setPostCode(matcher.group(1));
            }

            matcher = Pattern.compile(countryPattern).matcher(line);
            if (matcher.find()) {
                retailerAddressDTO.setCountry(matcher.group(1));
            }
        }

        employeeDataDTO.setRetailerAddressDTO(retailerAddressDTO);
        log.info("Employee Data: {}", employeeDataDTO);
        return employeeDataDTO;
    }
    private static void setCheckboxValue(String fieldName, boolean value) {

        AvanserOmniRewardsProgramsDTO rewardsProgramsDTO = new AvanserOmniRewardsProgramsDTO();
        RetailerDashBoardOneVoiceDTO dashBoardOneVoiceDTO = new RetailerDashBoardOneVoiceDTO();
        TrafficReportOminiStoreDTO reportOminiStoreDTO = new TrafficReportOminiStoreDTO();
        WorkPlaceSesimiDTO workPlaceSesimiDTO = new WorkPlaceSesimiDTO();

        switch (fieldName) {
            case "E2E":
                rewardsProgramsDTO.setE2e(value);
                break;
            case "VF17":
                rewardsProgramsDTO.setVf17(value);
                break;
            case "Web Promotions":
                rewardsProgramsDTO.setWebPromotions(value);
                break;
            case "MML":
                dashBoardOneVoiceDTO.setMml(value);
                break;
            case "DDP":
                dashBoardOneVoiceDTO.setDdp(value);
                break;
            case "VIDA/TIE":
                dashBoardOneVoiceDTO.setVida_tie(value);
                break;
            case "CDSID":
                reportOminiStoreDTO.setCdsid(value);
                break;
            case "Omni Vision":
                reportOminiStoreDTO.setOminiVision(value);
                break;
            case "Sales Cloud":
                reportOminiStoreDTO.setSalesCloud(value);
                break;
            case "Content Store":
                workPlaceSesimiDTO.setContentStore(value);
                break;
            case "QW90":
                workPlaceSesimiDTO.setQw90(value);
                break;
            case "VISTA Access":
                workPlaceSesimiDTO.setVistaAccess(value);
                break;
        }
        log.info("Reward programs:{}",rewardsProgramsDTO);
        log.info("DashBoard One Voice:{}",dashBoardOneVoiceDTO);
        log.info("Report Omini Store:{}",reportOminiStoreDTO);
        log.info("WorkPlace Sesimi:{}",workPlaceSesimiDTO);
    }

}


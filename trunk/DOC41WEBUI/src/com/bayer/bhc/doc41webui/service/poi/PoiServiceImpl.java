package com.bayer.bhc.doc41webui.service.poi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.bayer.bhc.doc41webui.common.exception.Doc41ServiceException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.ecim.foundation.dbx.UserChangeableDataCarrier;


public class PoiServiceImpl implements PoiService {
	
private Map<String,PoiMapper> mappers;
	
	public void setMappers(Map<String, PoiMapper> mappers) {
		this.mappers = mappers;
	}
	private PoiMapper getMapper(String name) throws Doc41ServiceException{
		if(mappers==null){
			throw new Doc41ServiceException("no mapper configured");
		}
		if(mappers.containsKey(name)){
			return mappers.get(name);
		} else {
			throw new Doc41ServiceException("mapper "+name+" not configured");
		}
	}

	@Override
	public <T extends UserChangeableDataCarrier> List<T> importExcel(String importName,
			InputStream inStream, List<T> existingDCs)
			throws Doc41ServiceException {
		try {
			PoiMapper mapper = getMapper(importName);
			Workbook wb = new XSSFWorkbook(inStream);
			List<T> newDCs = new ArrayList<T>();
			Sheet sheet = wb.getSheetAt(0);
			Class<? extends UserChangeableDataCarrier> dcClass = mapper.getDCClass();
			List<Method> setterMethods = getSetterMethods(mapper);
			int rowsToSkip=1;
			for (Row row : sheet) {
				if(rowsToSkip>0){
					rowsToSkip--;
				} else {
					if(row.getPhysicalNumberOfCells()>0){
						@SuppressWarnings("unchecked")
						T newInstance = (T) dcClass.newInstance();
						for (int c=0;c<setterMethods.size();c++) {
							Method setterMethod = setterMethods.get(c);
							Cell cell = row.getCell(c);
							setValueFromCell(cell,setterMethod,newInstance);
						}
						newDCs.add(newInstance);
					}
				}
			}
			
			String fixMethodName = mapper.getFixMethodName();
			if(fixMethodName!=null){
				for (T newDC : newDCs) {
					Method fixMethod = newDC.getClass().getMethod(fixMethodName);
					fixMethod.invoke(newDC);
				}
			}
			
			Map<String, T> remainingExisting = new HashMap<String, T>();
			if(existingDCs!=null){
				for (T dc : existingDCs) {
					remainingExisting.put(mapper.getObjectKey(dc), dc);
				}
			}
			
			List<T> dcsToStore = new ArrayList<T>();
			for (T newDC : newDCs) {
				String key = mapper.getObjectKey(newDC);
				T existingDC = null;
				if(key!=null){
					existingDC = remainingExisting.remove(key);
				}
				if(existingDC==null){
					setTechnicalValues(newDC, false);
					dcsToStore.add(newDC);
				} else {
					copyValues(newDC,existingDC,mapper.getColumnOrder());
					setTechnicalValues(existingDC, true);
					dcsToStore.add(existingDC);
				}
			}
			for (String key : remainingExisting.keySet()) {
				T dcToDelete = remainingExisting.get(key);
				
				boolean markedForDelete = mapper.markAsDeleted(dcToDelete);
				if(markedForDelete){
					dcsToStore.add(dcToDelete);
				}
			}
			return dcsToStore;
		} catch (IOException e) {
			throw new Doc41ServiceException(e.getMessage(), e);
		} catch (InstantiationException e) {
			throw new Doc41ServiceException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new Doc41ServiceException(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			throw new Doc41ServiceException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new Doc41ServiceException(e.getMessage(), e);
		}
	}
	
	private <T extends UserChangeableDataCarrier> void setValueFromCell(Cell cell, Method setterMethod, T newInstance) throws Doc41ServiceException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> type = setterMethod.getParameterTypes()[0];
		
		Object value;
		if(cell==null || cell.getCellType()==Cell.CELL_TYPE_BLANK){
			value = null;
		} else if(type.isAssignableFrom(BigDecimal.class)){
			value = BigDecimal.valueOf(cell.getNumericCellValue());
		} else if(type.isAssignableFrom(Integer.class)){
			if(cell.getCellType()==Cell.CELL_TYPE_STRING){
				String valueStr = cell.getStringCellValue();
				value = Integer.valueOf(valueStr);
			} else {
				double numValue = cell.getNumericCellValue();
				value = Integer.valueOf((int)numValue);
			} 
		} else if(type.isAssignableFrom(Long.class)){
			if(cell.getCellType()==Cell.CELL_TYPE_STRING){
				String valueStr = cell.getStringCellValue();
				value = Long.valueOf(valueStr);
			} else {
				double numValue = cell.getNumericCellValue();
				value = Long.valueOf((long)numValue);
			} 
		} else if(type.isAssignableFrom(Date.class)){
			value = cell.getDateCellValue();
		} else if(type.isAssignableFrom(Boolean.class)){
			if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN){
				boolean valueBool = cell.getBooleanCellValue();
				value = valueBool;
			} else {
				double numValue = cell.getNumericCellValue();
				int intValue = (int) numValue;
				value = Boolean.valueOf(intValue==1);
			} 
		} else if(type.isAssignableFrom(String.class)){
			if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
				double numValue = cell.getNumericCellValue();
				value = Double.toString(numValue);
			} else {
				value = cell.getStringCellValue();
			} 
		} else {
			throw new Doc41ServiceException("unsupported type: "+type+" in method: "+setterMethod.getName());
		}
		setterMethod.invoke(newInstance,value);
		
	}
	
	private <T extends UserChangeableDataCarrier> void copyValues(T fromDC, T toDC,String[] columnNames) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		for (int i = 0; i < columnNames.length; i++) {
			String colname = columnNames[i];
			Method getMethod = fromDC.getClass().getMethod("get"+Character.toUpperCase(colname.charAt(0))+colname.substring(1));
			Class<?> returnType = getMethod.getReturnType();
			Method setMethod = toDC.getClass().getMethod("set"+Character.toUpperCase(colname.charAt(0))+colname.substring(1),returnType);
			Object value = getMethod.invoke(fromDC);
			setMethod.invoke(toDC, value);
		}
	}
	
	private <T extends UserChangeableDataCarrier> void setTechnicalValues(T dc,boolean isUpdate) {
		String cwid = UserInSession.getCwid();
		Date now = new Date();
		if(!isUpdate){
			if(dc.getCreatedBy()==null){
				dc.setCreatedBy(cwid);
			}
			if(dc.getCreated()==null){
				dc.setCreated(now);
			}
		}
		dc.setChanged(now);
		dc.setChangedBy(cwid);
	}

	@Override
	public void exportExcel(String exportName, OutputStream outStream,
			List<? extends UserChangeableDataCarrier> dcs) throws Doc41ServiceException {
		try{
			PoiMapper mapper = getMapper(exportName);
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("Data");
			sheet.createFreezePane( 0,1 ); 

			CellStyle headerStyle = wb.createCellStyle();
			Font boldFont = wb.createFont();
			boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headerStyle.setFont(boldFont);
			headerStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
			headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

			String[] headerLabels = mapper.getHeaderLabels();
			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < headerLabels.length; i++) {
				String label = headerLabels[i];
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(label);
				cell.setCellStyle(headerStyle);
			}

			List<Method> getterMethods = getGetterMethods(mapper);

			CreationHelper createHelper = wb.getCreationHelper();
			short dateFormat = createHelper.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(0xe));
			
			CellStyle lineStyle = wb.createCellStyle();
			
			CellStyle lineDateStyle = wb.createCellStyle();
			lineDateStyle.cloneStyleFrom(lineStyle);
			lineDateStyle.setDataFormat(dateFormat);
			
			CellStyle altLineStyle = wb.createCellStyle();
			altLineStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			altLineStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			CellStyle altLineDateStyle = wb.createCellStyle();
			altLineDateStyle.cloneStyleFrom(altLineStyle);
			altLineDateStyle.setDataFormat(dateFormat);
			
			

			for (int r=0;r<dcs.size();r++) {
				UserChangeableDataCarrier dc = dcs.get(r);
				Row row = sheet.createRow(r+1);
				for(int c=0;c<getterMethods.size();c++){
					Method method = getterMethods.get(c);
					Cell cell = row.createCell(c);
					Object value = method.invoke(dc);
					Class<?> returnType = method.getReturnType();
					setValueInCell(cell,value,returnType,method.getName());
					if(r%2==0){
						if(Date.class.isAssignableFrom(returnType)){
							cell.setCellStyle(altLineDateStyle);
						} else {
							cell.setCellStyle(altLineStyle);
						}
					} else {
						if(Date.class.isAssignableFrom(returnType)){
							cell.setCellStyle(lineDateStyle);
						} else {
							cell.setCellStyle(lineStyle);
						}
					}
				}
			}

			//Column Formatting
			DataValidationHelper dataValidationHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
			for(int c=0;c<getterMethods.size();c++){
				sheet.autoSizeColumn(c);
				if(Boolean.class.isAssignableFrom(getterMethods.get(c).getReturnType())){
					DataValidationConstraint constraint = dataValidationHelper.createExplicitListConstraint(new String[]{"0", "1"});
					CellRangeAddressList addressList = new CellRangeAddressList(1, dcs.size()+100, c, c);
					DataValidation validation = dataValidationHelper.createValidation(constraint, addressList);
					validation.setShowErrorBox(true);
					sheet.addValidationData(validation);
				}
			}
			wb.write(outStream);
		} catch(IOException e){
			throw new Doc41ServiceException(e.getMessage(),e);
		} catch (SecurityException e) {
			throw new Doc41ServiceException(e.getMessage(),e);
		} catch (NoSuchMethodException e) {
			throw new Doc41ServiceException(e.getMessage(),e);
		} catch (IllegalArgumentException e) {
			throw new Doc41ServiceException(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			throw new Doc41ServiceException(e.getMessage(),e);
		} catch (InvocationTargetException e) {
			throw new Doc41ServiceException(e.getMessage(),e);
		}
	}
	
	private void setValueInCell(Cell cell, Object value, Class<?> returnType,String methodName) throws Doc41ServiceException {
		if(Number.class.isAssignableFrom(returnType)){//Number
			Number valueNum =(Number) value;
			if(valueNum!=null){
				cell.setCellValue(valueNum.doubleValue());
			}
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		} else if(Date.class.isAssignableFrom(returnType)){//Date
			Date valueDate = (Date) value;
			if(valueDate!=null){
				cell.setCellValue(valueDate);
			}
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		} else if(String.class.isAssignableFrom(returnType)){//String
			String valueStr = (String) value;
			if(valueStr!=null){
				cell.setCellValue(valueStr);
			}
			cell.setCellType(Cell.CELL_TYPE_STRING);
		} else if(Boolean.class.isAssignableFrom(returnType)){ //Boolean
			Boolean valueBoolean = (Boolean) value;
			double valueDouble;
			if(valueBoolean!=null){
				if(valueBoolean.booleanValue()){
					valueDouble = 1;
				} else {
					valueDouble = 0;
				}
				cell.setCellValue(valueDouble);
			}
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		} else {
			throw new Doc41ServiceException("unsupported data type: "+returnType+" in method: "+methodName);
		}
	}
	
	private List<Method> getGetterMethods(PoiMapper mapper) throws SecurityException, NoSuchMethodException {
		String[] columnOrder = mapper.getColumnOrder();
		Class<? extends UserChangeableDataCarrier> dcClass = mapper.getDCClass();
		List<Method> getterMethods = new ArrayList<Method>();
		for (String colName : columnOrder) {
			Method getMethod=dcClass.getMethod(createGetterName(colName));
			getterMethods.add(getMethod);
		}
		return getterMethods;
	}
	
	private List<Method> getSetterMethods(PoiMapper mapper) throws SecurityException, NoSuchMethodException {
		String[] columnOrder = mapper.getColumnOrder();
		Class<? extends UserChangeableDataCarrier> dcClass = mapper.getDCClass();
		List<Method> setterMethods = new ArrayList<Method>();
		for (String colName : columnOrder) {
			Method getMethod=dcClass.getMethod(createGetterName(colName));
			Class<?> type = getMethod.getReturnType();
			Method setMethod=dcClass.getMethod(createSetterName(colName),type);
			
			setterMethods.add(setMethod);
		}
		return setterMethods;
	}

	private String createGetterName(String column) {
		return "get"+Character.toUpperCase(column.charAt(0))+column.substring(1);
	}
	
	private String createSetterName(String column) {
		return "set"+Character.toUpperCase(column.charAt(0))+column.substring(1);
	}


}

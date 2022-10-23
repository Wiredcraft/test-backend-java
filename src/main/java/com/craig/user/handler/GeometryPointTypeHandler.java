package com.craig.user.handler;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.ByteOrderValues;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;
import com.vividsolutions.jts.io.WKTReader;

@MappedTypes({String.class})
@MappedJdbcTypes({JdbcType.OTHER})
public class GeometryPointTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType)
            throws SQLException {
                Geometry geo = null;
                try{
                    //String to Geometry
                    geo = new WKTReader(new GeometryFactory(new PrecisionModel())).read(s);
                    // Geometry to WKB
                    byte[] geometryBytes = new WKBWriter(2, ByteOrderValues.LITTLE_ENDIAN, false).write(geo);

                    byte[] wkb = new byte[geometryBytes.length+4];
                    wkb[0] = wkb[1] = wkb[2] = wkb[3] = 0;
                    System.arraycopy(geometryBytes, 0, wkb, 4, geometryBytes.length);
                    preparedStatement.setBytes(i,wkb);
                }catch (ParseException e){
                    
                }
        
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        try(InputStream inputStream = resultSet.getBinaryStream(columnName)){
			Geometry geo = getGeometryFromInputStream(inputStream);
			if(geo != null){
				return geo.toString();
			}
		}catch(Exception e){
			
		}
		return null;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try(InputStream inputStream = rs.getBinaryStream(columnIndex)){
			Geometry geo = getGeometryFromInputStream(inputStream);
			if(geo != null){
				return geo.toString();
			}
		}catch(Exception e){
			
		}
		return null;
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return "";
    }
    
    private Geometry getGeometryFromInputStream(InputStream inputStream) throws Exception {
 
        Geometry dbGeometry = null;
    
        if (inputStream != null) {
    
            //convert the stream to a byte[] array
            //so it can be passed to the WKBReader
            byte[] buffer = new byte[255];
    
            int bytesRead = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
    
            byte[] geometryAsBytes = baos.toByteArray();
    
            if (geometryAsBytes.length < 5) {
                throw new Exception("Invalid geometry inputStream - less than five bytes");
            }
    
            //first four bytes of the geometry are the SRID,
            //followed by the actual WKB.  Determine the SRID
            //here
            byte[] sridBytes = new byte[4];
            System.arraycopy(geometryAsBytes, 0, sridBytes, 0, 4);
            boolean bigEndian = (geometryAsBytes[4] == 0x00);
    
            int srid = 0;
            if (bigEndian) {
               for (int i = 0; i < sridBytes.length; i++) {
                  srid = (srid << 8) + (sridBytes[i] & 0xff);
               }
            } else {
               for (int i = 0; i < sridBytes.length; i++) {
                 srid += (sridBytes[i] & 0xff) << (8 * i);
               }
            }
    
            //use the JTS WKBReader for WKB parsing
            WKBReader wkbReader = new WKBReader();
    
            //copy the byte array, removing the first four
            //SRID bytes
            byte[] wkb = new byte[geometryAsBytes.length - 4];
            System.arraycopy(geometryAsBytes, 4, wkb, 0, wkb.length);
            dbGeometry = wkbReader.read(wkb);
            dbGeometry.setSRID(srid);
        }
    
        return dbGeometry;
    }
}

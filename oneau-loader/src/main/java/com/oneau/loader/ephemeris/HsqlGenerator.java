package com.oneau.loader.ephemeris;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

import java.util.List;

public class HsqlGenerator extends AbstractSqlGenerator {

    /* (non-Javadoc)
	 * @see com.oneau.loader.ephemeris.SqlGenerator#generateSchema()
	 */
    @Override
	public List<String> generateSchema() {
        return unmodifiableList(
                asList(
//                       "CREATE SCHEMA PUBLIC AUTHORIZATION DBA" + getStatementTerminator(),
                       "CREATE SCHEMA ONEAU AUTHORIZATION DBA" + getStatementTerminator(),
                       "CREATE MEMORY TABLE ONEAU.EPHEMERIS_HEADER(ID INTEGER NOT NULL PRIMARY KEY,NAME VARCHAR(64) NOT NULL,FILENAME VARCHAR(64) NOT NULL,KSIZE INTEGER NOT NULL,NUM_COEFF INTEGER NOT NULL,EPOCH_START NUMERIC NOT NULL,EPOCH_END NUMERIC NOT NULL,CONSTRAINT SYS_CT_46 UNIQUE(NAME),CONSTRAINT SYS_CT_47 UNIQUE(FILENAME))" + getStatementTerminator(),
                       "CREATE MEMORY TABLE ONEAU.EPHEMERIS_DATA(ID INTEGER NOT NULL PRIMARY KEY,FILENAME VARCHAR(64) NOT NULL,HEADER_ID INTEGER NOT NULL,CONSTRAINT SYS_CT_52 UNIQUE(HEADER_ID,FILENAME),CONSTRAINT SYS_FK_53 FOREIGN KEY(HEADER_ID) REFERENCES EPHEMERIS_HEADER(ID))" + getStatementTerminator(),
                       "CREATE MEMORY TABLE ONEAU.EPHEMERIS_INTERVAL(ID INTEGER NOT NULL PRIMARY KEY,RANGE_FROM NUMERIC NOT NULL,RANGE_TO NUMERIC NOT NULL,CONSTRAINT SYS_CT_59 UNIQUE(RANGE_FROM,RANGE_TO))" + getStatementTerminator(),
                       "CREATE MEMORY TABLE ONEAU.MEASURED_ITEM(ID INTEGER NOT NULL PRIMARY KEY,NAME VARCHAR(64) NOT NULL,DIMENSIONS INTEGER NOT NULL,CHEB_COEFFS INTEGER NOT NULL,COEFF_SETS INTEGER NOT NULL,CONSTRAINT SYS_CT_63 UNIQUE(NAME))" + getStatementTerminator(),
                       "CREATE MEMORY TABLE ONEAU.CONSTANT(ID INTEGER NOT NULL PRIMARY KEY,HEADER_ID INTEGER NOT NULL,NAME VARCHAR(64) NOT NULL,VALUE VARCHAR(64) NOT NULL,CONSTRAINT SYS_FK_67 FOREIGN KEY(HEADER_ID) REFERENCES EPHEMERIS_HEADER(ID),CONSTRAINT SYS_CT_68 UNIQUE(HEADER_ID,NAME))" + getStatementTerminator(),
                       "CREATE CACHED TABLE ONEAU.OBSERVATION(ID INTEGER NOT NULL PRIMARY KEY,FILE_ID INTEGER NOT NULL,MEASURED_ITEM_ID INTEGER NOT NULL,OBSERVATION_NUM INTEGER NOT NULL,INTERVAL_ID INTEGER,COEFFICIENT NUMERIC NOT NULL,CONSTRAINT SYS_FK_74 FOREIGN KEY(FILE_ID) REFERENCES EPHEMERIS_DATA(ID),CONSTRAINT SYS_FK_75 FOREIGN KEY(MEASURED_ITEM_ID) REFERENCES MEASURED_ITEM(ID),CONSTRAINT SYS_FK_76 FOREIGN KEY(INTERVAL_ID) REFERENCES EPHEMERIS_INTERVAL(ID))" + getStatementTerminator(),
                       "SET TABLE ONEAU.OBSERVATION INDEX'56621544 56621544 32 56621544 0'" + getStatementTerminator(),
//                       "CREATE USER SA PASSWORD ''" + getStatementTerminator(),
                       "CREATE USER ONEAU PASSWORD 'ONEAU'" + getStatementTerminator(),
//                       "GRANT DBA TO SA" + getStatementTerminator(),
                       "SET WRITE_DELAY 10" + getStatementTerminator(),
                       "SET SCHEMA ONEAU" + getStatementTerminator()
                )
        );
    }
    

	@Override
	protected String getStatementTerminator() {
		return "\n";
	}

}

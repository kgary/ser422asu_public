package edu.asupoly.ser422;

import java.io.File;

import edu.asupoly.ser422.Ser422DbWrapperException;

public interface IXRayService {
    public boolean readXRayImage(int id, File outfile) throws Ser422DbWrapperException;
    public boolean readXRayDiagnosis(int id, File outfile) throws Ser422DbWrapperException;

    public boolean writeXRayImage(int id, File file) throws Ser422DbWrapperException;
    public boolean writeXRayDiagnosis(int id, File file) throws Ser422DbWrapperException;
}

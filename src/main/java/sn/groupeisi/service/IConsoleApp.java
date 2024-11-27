package sn.groupeisi.service;

import javax.validation.Valid;
import java.sql.Date;

public interface IConsoleApp {
    <T> T saveEntity(@Valid T entity);
    String findDemandeByDate(Date date);
}

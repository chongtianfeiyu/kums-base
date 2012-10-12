package com.kurui.kums.base.database;

import java.util.List;
import com.kurui.kums.base.ListActionForm;

public interface BaseDAO {

	public abstract List list(String s);

	public abstract List list(String s, ListActionForm listactionform);
}

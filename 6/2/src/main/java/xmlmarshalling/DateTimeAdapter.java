package xmlmarshalling;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Date;
import java.text.SimpleDateFormat;

class DateTimeAdapter extends XmlAdapter<String, Date> {
    @Override
    public Date unmarshal(String xml) {
        return Date.valueOf(xml);
    }

    @Override
    public String marshal(Date object) {
        return new SimpleDateFormat("yyyy-MM-dd").format(object);
    }
}

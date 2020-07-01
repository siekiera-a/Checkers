package record;

class MoveData {

    private final String date;
    private final String fields;

    MoveData(String date, String fields) {
        this.date = date;
        this.fields = fields;
    }

    String getDate() {
        return date;
    }

    String getFields() {
        return fields;
    }

}
package CRUD.TechmasterCRUD.practice;

public class Major {
    private final int majorID;
    private final String majorName;
    private final Integer yearOfStart;
    private final int numberOfSemester;

    public Major(int majorID, String majorName, Integer yearOfStart, int numberOfSemester) {
        this.majorID = majorID;
        this.majorName = majorName;
        this.yearOfStart = yearOfStart;
        this.numberOfSemester = numberOfSemester;
    }



    public int getMajorID() {
        return majorID;
    }

    public String getMajorName() {
        return majorName;
    }

    public Integer getYearOfStart() {
        return yearOfStart;
    }

    public int getNumberOfSemester() {
        return numberOfSemester;
    }
}

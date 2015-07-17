package inf1019.rnb.aufgabe3;

/**
 * Created by rnb on 10.04.2015.
 */
/*
Represents a set of selectable categories
todo dynamic adt
 */
public enum Category {
    WORK {
        public String toString() { return "Work"; }
    },
    EDUCATION {
        public String toString() { return "Education"; }
    },
    SPORT {
        public String toString() { return "Sport"; }
    },
    OTHER {
        public String toString() { return "Other"; }
    },
}

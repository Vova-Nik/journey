package org.hillel.dto.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter

@AllArgsConstructor

public class QueryParam {
    private String sortColumn;
    private String sortDirection;
    private String filterField;
    private String filterValue;
    private FilterOperation filterOperation;
    private int pageNumber;
    private int pageSize;
    private final int DEFAULT_PAGE_SIZE = 8;

    public QueryParam() {

        this.sortColumn = "id";
        this.sortDirection = "asc";
        this.filterField = "name";
        this.filterValue = "*";
        this.filterOperation = FilterOperation.GREATER;
        this.pageNumber = 0;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public void setSortColumn(String sortColumn) {
        if (StringUtils.hasText(sortColumn))
            this.sortColumn = sortColumn;
    }

    public void setSortDirection(String sortDirection) {
        if (StringUtils.hasText(sortDirection))
            this.sortDirection = sortDirection;
    }

    public void setFilterField(String filterField) {
        if (StringUtils.hasText(filterField))
            this.filterField = filterField;
    }

    public void setFilterValue(String filterValue) {
        if (StringUtils.hasText(filterValue))
            this.filterValue = filterValue;
    }

    public void setFilterOperation(FilterOperation filterOperation) {
        if (StringUtils.hasText(filterOperation.toString()))
            this.filterOperation = filterOperation;
    }

    public void setFilterOperation(String filterOperation) {
        if (StringUtils.hasText(filterOperation)) {
            filterOperation = filterOperation.trim();
            filterOperation = filterOperation.toUpperCase();
            filterOperation = filterOperation.replace(' ', '_');
            filterOperation = filterOperation.replace('-', '_');

            switch (filterOperation) {
                case "EQUALS":
                case "EQUAL":
                case "SAME":
                case "AS":
                    this.filterOperation = FilterOperation.EQUALS;
                    break;

                case "NOT_EQUALS":
                case "NOT_EQUAL":
                case "NO_EQUALS":
                case "NO_EQUAL":
                    this.filterOperation = FilterOperation.NOT_EQUALS;
                    break;

                case "GREATER":
                case "MORE":
                case "BIGGER":
                case ">":
                    this.filterOperation = FilterOperation.GREATER;
                    break;

                case "LESS":
                case "SMALLER":
                case "<":
                    this.filterOperation = FilterOperation.LESS;
                    break;

                case "LIKE":
                case "As":
                case "=":
                case "==":
                    this.filterOperation = FilterOperation.LIKE;
                    break;

                case "IN":
                    this.filterOperation = FilterOperation.IN;
                    break;

                default:
                    this.filterOperation = FilterOperation.GREATER;
            }
        }
    }

    public void setPageNumber(Integer pageNumber) {
        if (pageNumber == null || pageNumber < 0)
            return;
        this.pageNumber = pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        if (StringUtils.hasText(pageNumber)) {
            try {
                this.pageNumber = Integer.parseInt(pageNumber);
            } catch (NumberFormatException ignored) {
            }
        }
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null || pageSize < 0)
            return;
        this.pageSize = pageSize;
    }

    public void setPageSize(String pageSize) {
        if (StringUtils.hasText(pageSize)) {
            try {
                this.pageSize = Integer.parseInt(pageSize);
            } catch (NumberFormatException ignored) {

            }
        }
    }

    public void buildQueryParam(String sortColumn,
                                String sortDirection,
                                String filterField,
                                String filterValue,
                                String pageNumber,
                                String pageSize,
                                String filterOperation) {
        setSortColumn(sortColumn);
        setSortDirection(sortDirection);
        setFilterField(filterField);
        setFilterValue(filterValue);
        setPageNumber(pageNumber);
        setPageSize(pageSize);
        setFilterOperation(filterOperation);
    }

    public void nextPage() {
        pageNumber++;
    }

    public void previousPage() {
        if (pageNumber > 0)
            pageNumber--;
    }

    @Override
    public String toString() {
        return "QueryParam{" +
                "sortColumn='" + sortColumn + '\'' +
                ", sortDirection='" + sortDirection + '\'' +
                ", filterField='" + filterField + '\'' +
                ", filterValue='" + filterValue + '\'' +
                ", filterOperation=" + filterOperation +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", DEFAULT_PAGE_SIZE=" + DEFAULT_PAGE_SIZE +
                '}';
    }
}

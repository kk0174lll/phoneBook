package ru.cbr.demo.worksPlace.web.models;

public class Pagination
{

  /**
   * номер первой записи таблицы для отображения
   */
  private Integer rowBegin;
  /**
   * номер последней записи таблицы для отображения
   */
  private Integer rowEnd;

  private Pagination(Integer rowBegin, Integer rowEnd)
  {
    this.rowBegin = rowBegin;
    this.rowEnd = rowEnd;
  }

  /**
   * @param pageNum          - номер текущей страницы
   * @param pagePaymentCount - количество записей на странице
   * @param count            - общее количество записей таблицы
   * @return объект класса Pagination
   */
  public static Pagination create(Integer pageNum, Integer pagePaymentCount, Integer count)
  {
    if (pageNum == null) {
      pageNum = 1;
    }
    if (pagePaymentCount == null) {
      pagePaymentCount = 20;
    }
    int rowBegin, rowEnd;
    if (pagePaymentCount * (pageNum - 1) >= count) {
      pageNum = count / pagePaymentCount + count % pagePaymentCount > 0 ? 1 : 0;
    }
    if (count < pagePaymentCount) {
      rowBegin = 1;
      rowEnd = count;
    } else {
      rowBegin = (pageNum - 1) * pagePaymentCount + 1;
      rowEnd = rowBegin + pagePaymentCount - 1;
      if (rowEnd > count) {
        rowEnd = count;
      }
    }
    return new Pagination(rowBegin, rowEnd);
  }

  public Integer getRowBegin()
  {
    return rowBegin;
  }

  public Integer getRowEnd()
  {
    return rowEnd;
  }
}

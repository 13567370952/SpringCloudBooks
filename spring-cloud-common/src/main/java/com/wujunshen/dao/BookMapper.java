package com.wujunshen.dao;

import com.wujunshen.entity.Book;
import com.wujunshen.entity.BookCriteria;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbggenerated Mon Aug 07 22:04:12 CST 2017
     */
    int countByExample(BookCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbggenerated Mon Aug 07 22:04:12 CST 2017
     */
    int deleteByExample(BookCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbggenerated Mon Aug 07 22:04:12 CST 2017
     */
    int deleteByPrimaryKey(Integer bookId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbggenerated Mon Aug 07 22:04:12 CST 2017
     */
    int insert(Book record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbggenerated Mon Aug 07 22:04:12 CST 2017
     */
    int insertSelective(Book record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbggenerated Mon Aug 07 22:04:12 CST 2017
     */
    List<Book> selectByExample(BookCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbggenerated Mon Aug 07 22:04:12 CST 2017
     */
    Book selectByPrimaryKey(Integer bookId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbggenerated Mon Aug 07 22:04:12 CST 2017
     */
    int updateByExampleSelective(@Param("record") Book record, @Param("example") BookCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbggenerated Mon Aug 07 22:04:12 CST 2017
     */
    int updateByExample(@Param("record") Book record, @Param("example") BookCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbggenerated Mon Aug 07 22:04:12 CST 2017
     */
    int updateByPrimaryKeySelective(Book record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table simple_book
     *
     * @mbggenerated Mon Aug 07 22:04:12 CST 2017
     */
    int updateByPrimaryKey(Book record);
}
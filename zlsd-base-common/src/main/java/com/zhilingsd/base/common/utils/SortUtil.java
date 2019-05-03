package com.zhilingsd.base.common.utils;

import com.google.common.base.CaseFormat;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 2018/6/5
 * Time: 17:58
 *
 * @author: lan.qing
 * Description: 案件管理、 作业管理、还款管理排序所抽取的函数
 */
public class SortUtil {

    /**
     * Describe: 驼峰转下划线
     * Date: 2018/6/6
     * Time: 15:11
     *
     * @author: lan.qing
     **/
    public static String getUnderLineString(String str) {
        if (StringUtils.isNotBlank(str)) {
            char[] chars = str.toCharArray();
            StringBuilder sb = new StringBuilder();
            sb.append(Character.toLowerCase(chars[0]));
            int length = chars.length;
            for (int i = 1; i < length; i++) {
                if (Character.isUpperCase(chars[i])) {
                    sb.append("_");
                }
                sb.append(Character.toLowerCase(chars[i]));
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    public static String getCamelCase(String s) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s);
    }

    public static String getSortSql(String sql, String oldSort, String sortField, String sequence) {
        StringBuilder sortSql = new StringBuilder();
        if (StringUtils.isNotBlank(sortField) && StringUtils.isNotBlank(sequence)) {
            sortSql.append("select * from(");
            sortSql.append(sql);
            //子查询别名
            sortSql.append(")sortTable").append(" order by ").append(sortField).
                    append(" ").append(sequence).append(" nulls last");
        } else {
            sortSql.append(sql);
            sortSql.append(oldSort);
        }
        return sortSql.toString();
    }

    public static void main(String[] args) {
        String sql = "SELECT coalesce(sum(t.commit_money),0) commitMoney,coalesce(sum(t.latest_debt_money),0) latestDebtMoney\tFROM (SELECT m.batch_code,m.case_id,m.case_code,m.department_id,m.department_name,m.staff_name,m.staff_collection_id,m.collection_status,m.assigne_allot_status,i.loan_institution,i.product_name,i.borrower_name,i.borrower_idnumber,i.borrower_age,i.borrower_gender,i.borrower_phone,i.overdue_day,i.receivable_age,i.bacth_times,i.total_debt_money,i.commit_money,i.latest_debt_money,i.commit_date,i.limit_date,i.case_area,i.registered_address,i.follow_date,i.case_status,i.tel_status,i.tel_hidden,i.bill_code billCode FROM rc_cp_assignee_case_manage m LEFT OUTER JOIN rc_cp_case_info i on m.case_id=i.id WHERE (m.department_id in (SELECT id from rc_cp_organization where path like ( (SELECT path from rc_cp_organization where id = (SELECT organization_id from rc_cp_map_user_organization where user_id =2 and type = 0)) || ':%' ) or path = (SELECT path from rc_cp_organization where id = (SELECT organization_id from rc_cp_map_user_organization where user_id =2 and type = 0))) and m.company_sign = 'cuishouA' AND m.company_sign = 'cuishouA' AND i.case_status not in (4,11)) order by m.assigne_allot_status asc , m.case_code desc ) t ";
        System.out.println(removeOrderBy(sql));
    }

    /**
     * @Description: sql语句去掉order by
     * @Param:
     * @return:
     * @Author: wuzs
     * @Date: 2018/9/17 18:47
     */

    public static String removeOrderBy(String sql) {
        Select select = null;
        try {
            Statement stmt = CCJSqlParserUtil.parse(sql);
            select = (Select) stmt;
            SelectBody selectBody = select.getSelectBody();
            processSelectBody(selectBody);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return select == null ? sql : select.toString();
        }

    }

    public static void processSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                processSelectBody(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (!orderByHashParameters(operationList.getOrderByElements())) {
                operationList.setOrderByElements(null);
            }
        }
    }

    public static void processPlainSelect(PlainSelect plainSelect) {
        if (!orderByHashParameters(plainSelect.getOrderByElements())) {
            plainSelect.setOrderByElements(null);
        }
        if (plainSelect.getFromItem() != null) {
            processFromItem(plainSelect.getFromItem());
        }
        if (plainSelect.getJoins() != null && plainSelect.getJoins().size() > 0) {
            List<Join> joins = plainSelect.getJoins();
            for (Join join : joins) {
                if (join.getRightItem() != null) {
                    processFromItem(join.getRightItem());
                }
            }
        }
    }

    public static void processFromItem(FromItem fromItem) {
        //TODO 待修复 zr 20190503
//        if (fromItem instanceof SubJoin) {
//            SubJoin subJoin = (SubJoin) fromItem;
//            if (subJoin.getJoin() != null) {
//                if (subJoin.getJoin().getRightItem() != null) {
//                    processFromItem(subJoin.getJoin().getRightItem());
//                }
//            }
//            if (subJoin.getLeft() != null) {
//                processFromItem(subJoin.getLeft());
//            }
//        } else if (fromItem instanceof SubSelect) {
//            SubSelect subSelect = (SubSelect) fromItem;
//            if (subSelect.getSelectBody() != null) {
//                processSelectBody(subSelect.getSelectBody());
//            }
//        } else if (fromItem instanceof ValuesList) {
//
//        } else if (fromItem instanceof LateralSubSelect) {
//            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
//            if (lateralSubSelect.getSubSelect() != null) {
//                SubSelect subSelect = lateralSubSelect.getSubSelect();
//                if (subSelect.getSelectBody() != null) {
//                    processSelectBody(subSelect.getSelectBody());
//                }
//            }
//        }
        //Table时不用处理
    }

    public static boolean orderByHashParameters(List<OrderByElement> orderByElements) {
        if (orderByElements == null) {
            return false;
        }
        for (OrderByElement orderByElement : orderByElements) {
            if (orderByElement.toString().toUpperCase().contains("?")) {
                return true;
            }
        }
        return false;
    }
}

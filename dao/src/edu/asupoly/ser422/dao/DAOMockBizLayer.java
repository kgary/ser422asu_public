package edu.asupoly.ser422.dao;

import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import edu.asupoly.ser422.dao.daos.EditorDAO;
import edu.asupoly.ser422.dao.valueobject.EditorVO;
import edu.asupoly.ser422.dao.valueobject.TitleVO;

/**
 * @author kgary
 *
 * In a DAO pattern you usually have some business object accessing
 * (requesting) data from the data access tier. This class represents
 * a mock object of the business tier in order to demonstrate patterns
 * for access the data tier via DAOs
 */
public class DAOMockBizLayer {
    private static Hashtable<Long,ValueObject> __cache = new Hashtable<Long,ValueObject>();

    // might as well stay stateless
    public static synchronized EditorVO createEditor(String fname, String mname, String lname)
    	throws Exception
    {
        DAOObject eDAO = ConnectionFactory.getDAO("edu.asupoly.ser422.dao.daos.EditorDAO");
        EditorVO eVO = (EditorVO)eDAO.createValueObject();

        // Here you typically might do something interesting
        eVO.setFirstName(fname);
        eVO.setLastName(lname);
        eVO.setMiddleName(mname);
        // Note: All we did was create it. We didn't persist it
        // Shove it in our mock object cache
        __cache.put(new Long(eVO.getEditorId()), eVO);

        return eVO;
    }

    public static synchronized TitleVO createTitle(String descr, float cost)
    throws Exception
    {
        DAOObject tDAO = ConnectionFactory.getDAO("edu.asupoly.ser422.dao.daos.TitleDAO");
        TitleVO tVO = (TitleVO)tDAO.createValueObject();

        // Here you typically might do something interesting
        tVO.setTitleDescr(descr);
        tVO.setTitleCost(cost);

        // Note: All we did was create it. We didn't persist it
        // Shove it in our mock object cache
        __cache.put(new Long(tVO.getTitleId()), tVO);

        return tVO;
    }

    public static synchronized void insert(ValueObject vo) throws Exception {
        DAOObject dao = getDAO(vo);
        dao.insert(vo);
    }

    public static synchronized void update(ValueObject vo) throws Exception {
        DAOObject dao = getDAO(vo);
        dao.update(vo);
    }

    public static synchronized void delete(ValueObject vo) throws Exception {
        DAOObject dao = getDAO(vo);
        dao.delete(vo);
    }

    public static ValueObject findEditor(String pKey) throws Exception {
        DAOObject dao = ConnectionFactory.getDAO("edu.asupoly.ser422.dao.daos.EditorDAO");
        return dao.findByPrimaryKey(pKey);
    }
    // Note we have to use the specific type here to know the method
    public static Map<Long,EditorVO> findEditorByTitle(String title) throws Exception {
        EditorDAO eDAO = (EditorDAO)ConnectionFactory.getDAO("edu.asupoly.ser422.dao.daos.EditorDAO");
        return eDAO.findEditorByTitle(title);
    }

    public static ValueObject findTitle(String pKey) throws Exception {
        DAOObject dao = ConnectionFactory.getDAO("edu.asupoly.ser422.dao.daos.TitleDAO");
        return dao.findByPrimaryKey(pKey);
    }

    public static void printCache() {
        // prints out all the VOs in the cache, persisted or not
        System.out.println("CACHE DUMP");
        ValueObject vo = null;
        Iterator<ValueObject> iter = __cache.values().iterator();
        while (iter.hasNext()) {
            vo = (ValueObject)iter.next();
            System.out.println(vo.toString());
        }
    }

    private static DAOObject getDAO(ValueObject vo) throws Exception {
        String stype = null;

        if (vo instanceof EditorVO) {
            stype = "edu.asupoly.ser422.dao.daos.EditorDAO";
        }
        else if (vo instanceof TitleVO) {
            stype = "edu.asupoly.ser422.dao.daos.TitleDAO";
        }
        else {
            throw new Exception("Unknown VO given to service");
        }
        return ConnectionFactory.getDAO(stype);
    }

    public static void main(String[] args) {
        try {
            // Create some editor VOs
            EditorVO editor1 = createEditor("Bill", "E", "Bobb");
            EditorVO editor2 = createEditor("Susie", "J", "Queue");
            EditorVO editor3 = createEditor("George", "W", "Bush");
            EditorVO editor4 = createEditor("John", "", "Kerry");

            // and some titles
            TitleVO title1 = createTitle("Advanced Databases", 39.99f);
            TitleVO title2 = createTitle("Beginning Servlets", 19.95f);

            // Associate editors to titles
            Map<Long, EditorVO> et1 = new HashMap<Long, EditorVO>();
            Map<Long, EditorVO> et2 = new HashMap<Long, EditorVO>();

            // really should impl Hashcode on these VOs
            et1.put(new Long(editor1.getEditorId()), editor1);
            et1.put(new Long(editor2.getEditorId()), editor2);
            et2.put(new Long(editor3.getEditorId()), editor3);
            et2.put(new Long(editor4.getEditorId()), editor4);

            title1.setEditors(et1);
            title2.setEditors(et2);

            // OK, VOs all set, what trouble can we get in?
            // first let's add them to the persistent store
            // What is the Transaction Demarcation issue here?
            insert(editor1);
            insert(editor2);
            insert(editor3);
            insert(editor4);
            insert(title1);
            insert(title2);
            // Note how we had to do the editor inserts before the title
            // inserts or we would have gotten a foreign key problem in TitleDAOBean
            // on the Title_Editor table - we are not totally opaque (encapsulated)!

            // dump
            printCache();

            // Let's go find a VO of each type
            EditorVO newEditor1 = (EditorVO)findEditor(""+editor1.getEditorId());
            EditorVO newEditor3 = (EditorVO)findEditor(""+editor1.getEditorId());
            TitleVO  newTitle1  = (TitleVO)findTitle(""+title1.getTitleId());
            //Map<Long,EditorVO> etMap = findEditorByTitle(""+newTitle1.getTitleId());

            // dump this stuff out
            System.out.println("newEditor1: " + newEditor1.toString());
            System.out.println("newEditor3: " + newEditor3.toString());
            System.out.println("newTitle1: " + newTitle1.toString());

            // modify VO object state
            newEditor1.setFirstName("Joey");
            newEditor3.setLastName("Barry");
            newTitle1.setTitleCost(49.99f);

            // dump them again
            System.out.println("newEditor1: " + newEditor1.toString());
            System.out.println("newEditor3: " + newEditor3.toString());
            System.out.println("newTitle1: " + newTitle1.toString());

            // update the persistent store
            update(newEditor1);
            update(newEditor3);
            update(newTitle1);
            // Note what happens if we had two threads going at the same time trying
            // to update the same object. Our flag should help, but we would also
            // have to add code to check the row version number within the transaction,
            // effectively pushing transaction semantics to the client side - and
            // would it horizontally scale? Messy!
            printCache();

            // we could do a delete here too...
            delete(editor1);
            delete(editor2);
            delete(editor3);
            delete(editor4);
            delete(title1);
            delete(title2);

            // all done!
            printCache();

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }
}

package CuoiKiOOP;

import Model.DanhMuc;
import Model.SanPham;
import Service.DanhMucService;
import Service.SanPhamService;

import javax.management.ValueExp;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

public class QuanLiSanPham extends JFrame{

    JList<DanhMuc>listDanhMuc;

    JButton btnThemMoiDanhMuc, btnChinhSuaDanhMuc, btnXoaDanhMuc;

    DefaultTableModel dtmSanPham;
    JTable tblSanPham;

    JComboBox<DanhMuc>cboDanhMuc;

    JTextField txtMasp, txtTensp, txtSoluong, txtDonGia;

    JButton btnTaoMoiSp, btnLuuSp,btnXoaSp;

    ArrayList<SanPham>dsSp = new ArrayList<>();

    DanhMuc dmSelected=null;

    public QuanLiSanPham (String title){
        super (title);
        addControl();
        addEvents();

        hienThiDanhMucLenList();

    }

    private void hienThiDanhMucLenList() {
        DanhMucService dmService = new DanhMucService() ;
        Vector<DanhMuc>vec=dmService.DocToanBoDanhMuc();
        listDanhMuc.setListData(vec);
        cboDanhMuc.removeAllItems();
        for(DanhMuc dm:vec){
            cboDanhMuc.addItem(dm);
        }

    }

    private void addEvents() {
        listDanhMuc.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listDanhMuc.getSelectedValue()==null)return ;
                dmSelected =listDanhMuc.getSelectedValue();
                SanPhamService spService= new SanPhamService();
               dsSp= spService.docSanPhamTheoDanhMuc(
                        listDanhMuc.getSelectedValue().getMaDM());

                dtmSanPham.setRowCount(0);
                for(SanPham sp:dsSp){
                    Vector<Object>vec=new Vector<Object>();
                    vec.add(sp.getMaSp());
                    vec.add(sp.getTenSp());
                    vec.add(sp.getSoLuong());
                    vec.add(sp.getDonGia());

                    dtmSanPham.addRow(vec);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        tblSanPham.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                int row= tblSanPham.getSelectedRow();
                if(row==-1)return;
                SanPham sp= dsSp.get(row);
                txtMasp.setText(sp.getMaSp());
                txtTensp.setText(sp.getTenSp());
                txtSoluong.setText(sp.getSoLuong()+"");
                txtDonGia.setText(sp.getDonGia()+"");


            }
        });
        btnTaoMoiSp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtDonGia.setText("");
                txtMasp.setText("");
                txtSoluong.setText("");
                txtTensp.setText("");
                txtMasp.requestFocus();

            }
        });
        btnLuuSp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xuLyLuuSanPham();

            }
        });
    }
    protected void xuLyLuuSanPham(){
        SanPham sp= new SanPham();
        sp.setMaDM(dmSelected.getMaDM());
        sp.setMaSp(txtMasp.getText());
        sp.setTenSp(txtTensp.getText());
        SanPhamService spService= new SanPhamService();
        if (spService.luuSanPham(sp)>0){
            JOptionPane.showMessageDialog(null,"Luu thanh cong");
        }
        else{
            JOptionPane.showMessageDialog(null,"Luu that bai");

        }
    }

    private void addControl() {
        Container con=  getContentPane();
        con.setLayout(new BorderLayout());
        JPanel  pnLeft= new JPanel();
        setPreferredSize(new Dimension(300,0));
        JPanel pnRight=new JPanel();

        JSplitPane sp= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight);
        sp.setOneTouchExpandable(true);
        con.add(sp,BorderLayout.CENTER);

        pnLeft.setLayout(new BorderLayout());
        listDanhMuc= new JList<DanhMuc>();
        JScrollPane scListDanhMuc= new JScrollPane(listDanhMuc,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pnLeft.add(scListDanhMuc, BorderLayout.CENTER);

        TitledBorder borderListDm=
                new TitledBorder(BorderFactory.createLineBorder(Color.CYAN),
                        "DANH MUC SAN PHAM");
        listDanhMuc.setBorder(borderListDm);

        btnThemMoiDanhMuc= new JButton("Them DANH MUC");
        btnChinhSuaDanhMuc= new JButton("Cap nhat DANH MUC");
        btnXoaDanhMuc= new JButton("Xoa DANH MUC");
        JPanel pnButtonDanhMuc =new JPanel();
        pnButtonDanhMuc.add(btnThemMoiDanhMuc);
        pnButtonDanhMuc.add(btnChinhSuaDanhMuc);
        pnButtonDanhMuc.add(btnXoaDanhMuc);

        pnLeft.add(pnButtonDanhMuc,BorderLayout.SOUTH);

        pnRight.setLayout(new BorderLayout());
        JPanel pnTopOfRight= new JPanel();
        pnTopOfRight.setLayout(new BorderLayout());
        pnRight.add(pnTopOfRight, BorderLayout.CENTER);
        pnTopOfRight.setPreferredSize(new Dimension(0, 300));

        dtmSanPham= new DefaultTableModel();
        dtmSanPham.addColumn("Ma san pham");
        dtmSanPham.addColumn("Ten san pham");
        dtmSanPham.addColumn("So luong");
        dtmSanPham.addColumn("Don gia");
        tblSanPham= new JTable(dtmSanPham);
        JScrollPane scTable= new JScrollPane(tblSanPham,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pnTopOfRight.add(scTable, BorderLayout.CENTER);

        JPanel pnBottomOfRight= new JPanel();
        pnBottomOfRight.setLayout(new BoxLayout(pnBottomOfRight, BoxLayout.Y_AXIS));
        pnRight.add(pnBottomOfRight, BorderLayout.SOUTH);

        JPanel pnDanhMuc= new JPanel();
        pnDanhMuc.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblDanhMuc= new JLabel("Danh muc:");
        cboDanhMuc= new JComboBox<DanhMuc>();
        cboDanhMuc.setPreferredSize(new Dimension(350, 30));
        pnDanhMuc.add(lblDanhMuc);
        pnDanhMuc.add(cboDanhMuc);
        pnBottomOfRight.add(pnDanhMuc);

        JPanel pnMaSp= new JPanel();
        pnMaSp.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblMaSp= new JLabel("Ma SP");
        txtMasp= new JTextField(30);
        pnMaSp.add(lblMaSp);
        pnMaSp.add(txtMasp);
        pnBottomOfRight.add(pnMaSp);

        JPanel pnTenSp= new JPanel();
        pnTenSp.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTenSp= new JLabel("Ten SP");
        txtTensp= new JTextField(30);
        pnTenSp.add(lblTenSp);
        pnTenSp.add(txtTensp);
        pnBottomOfRight.add(pnTenSp);

        JPanel pnSoluong= new JPanel();
        pnSoluong.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblSoluong= new JLabel("So luong");
        txtSoluong= new JTextField(30);
        pnSoluong.add(lblSoluong);
        pnSoluong.add(txtSoluong);
        pnBottomOfRight.add(pnSoluong);

        JPanel pnDonGia= new JPanel();
        pnDonGia.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblDonGia= new JLabel("Don gia");
        txtDonGia= new JTextField(30);
        pnDonGia.add(lblDonGia);
        pnDonGia.add(txtDonGia);
        pnBottomOfRight.add(pnDonGia);

        JPanel pnButtonSanPham = new JPanel();
        pnButtonSanPham.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnTaoMoiSp= new JButton("Tao moi");
        btnLuuSp= new JButton("Luu san pham");
        btnXoaSp= new JButton("Xoa san pham");
        pnButtonSanPham.add(btnTaoMoiSp);
        pnButtonSanPham.add(btnLuuSp);
        pnButtonSanPham.add(btnXoaSp);
        pnBottomOfRight.add(pnButtonSanPham);

        lblMaSp.setPreferredSize(lblSoluong.getPreferredSize());
        lblTenSp.setPreferredSize(lblSoluong.getPreferredSize());
        lblDonGia.setPreferredSize(lblSoluong.getPreferredSize());
    }

    public void showWindow(){
        this.setSize(800,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PaymentFrame extends JFrame {
    private List<BookingFrame.OrderItem> orderItems;
    private int totalAmount;
    private String bookingDate;
    private String bookingTime;
    private JCheckBox termsCheckbox;

    public PaymentFrame(List<BookingFrame.OrderItem> items, int total, String date, String time) {
        this.orderItems = items;
        this.totalAmount = total;
        this.bookingDate = date;
        this.bookingTime = time;
        initComponents();
    }

    private void initComponents() {
        setTitle("ชำระเงิน");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Bar
        JPanel topBar = createTopBar();

        // Main Content
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Left Panel - สรุปคำสั่งซื้อ
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 30);
        mainPanel.add(createLeftPanel(), gbc);

        // Right Panel - วิธีการชำระเงิน
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 30, 0, 0);
        mainPanel.add(createRightPanel(), gbc);

        add(topBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        topBar.setBackground(new Color(0, 204, 204));
        topBar.setPreferredSize(new Dimension(1000, 60));

        JLabel phoneLabel = new JLabel("📞 01-234-5678");
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        JLabel lineLabel = new JLabel("💬 @Laundry Clean & Fresh");
        lineLabel.setForeground(Color.WHITE);
        lineLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        JLabel timeLabel = new JLabel("⏰ Mon - Sat 8:00 - 16:00");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

        JButton userButton = new JButton("จิรนันต์ เจียงบัติ ▼");
        userButton.setBackground(Color.WHITE);
        userButton.setForeground(new Color(0, 204, 204));
        userButton.setFont(new Font("Tahoma", Font.BOLD, 12));
        userButton.setFocusPainted(false);
        userButton.setBorderPainted(false);
        userButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        userButton.setPreferredSize(new Dimension(150, 30));

        topBar.add(phoneLabel);
        topBar.add(lineLabel);
        topBar.add(timeLabel);
        topBar.add(Box.createHorizontalGlue());
        topBar.add(userButton);

        return topBar;
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(400, 500));

        // Title
        JLabel titleLabel = new JLabel("สรุปคำสั่งซื้อ");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));

        panel.add(titleLabel);

        // Total amount at top
        JPanel totalTopPanel = new JPanel(new BorderLayout());
        totalTopPanel.setBackground(Color.WHITE);
        totalTopPanel.setMaximumSize(new Dimension(400, 40));
        totalTopPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel totalTopLabel = new JLabel("฿ " + String.format("%,d", totalAmount));
        totalTopLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
        totalTopLabel.setForeground(new Color(0, 150, 150));

        totalTopPanel.add(totalTopLabel, BorderLayout.WEST);
        panel.add(totalTopPanel);
        panel.add(Box.createVerticalStrut(20));

        // Terms checkbox
        termsCheckbox = new JCheckBox("<html>ข้าพเจ้ายืนยันว่าพอใจที่จะใช้งานและส่งมอบเครื่องนอนและเสื้อผ้าให้แกร่บริการซักรีดสามารถทำการบันทึกข้อมูลเรียบร้อย</html>");
        termsCheckbox.setFont(new Font("Tahoma", Font.PLAIN, 12));
        termsCheckbox.setBackground(Color.WHITE);
        termsCheckbox.setAlignmentX(Component.LEFT_ALIGNMENT);
        termsCheckbox.setMaximumSize(new Dimension(400, 60));

        panel.add(termsCheckbox);
        panel.add(Box.createVerticalStrut(30));

        // Divider
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(400, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(separator);
        panel.add(Box.createVerticalStrut(20));

        // Booking details section
        JLabel detailsTitle = new JLabel("เลือกวิธีการชำระเงิน");
        detailsTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        detailsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(detailsTitle);
        panel.add(Box.createVerticalStrut(15));

        // Order items
        for (BookingFrame.OrderItem item : orderItems) {
            panel.add(createOrderItemPanel(item));
            panel.add(Box.createVerticalStrut(10));
        }

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createOrderItemPanel(BookingFrame.OrderItem item) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(400, 50));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left - Item name and service
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(item.itemName);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));

        JLabel serviceLabel = new JLabel(item.serviceType);
        serviceLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        serviceLabel.setForeground(Color.GRAY);

        leftPanel.add(nameLabel);
        leftPanel.add(serviceLabel);

        // Center - Quantity
        JLabel qtyLabel = new JLabel(item.serviceType + " " + item.quantity);
        qtyLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        // Right - Price
        JLabel priceLabel = new JLabel("฿" + item.getTotalPrice());
        priceLabel.setFont(new Font("Tahoma", Font.BOLD, 15));

        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(qtyLabel, BorderLayout.CENTER);
        panel.add(priceLabel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(250, 250, 250));
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));
        panel.setPreferredSize(new Dimension(450, 500));

        // Title
        JLabel titleLabel = new JLabel("เลือกวิธีการชำระเงิน");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(20));

        // QR PromptPay option
        JPanel qrOption = createPaymentOption("QR PromptPay", "qr_promptpay");
        panel.add(qrOption);
        panel.add(Box.createVerticalStrut(15));

        // Cash option
        JPanel cashOption = createPaymentOption("เงินสด", "cash");
        panel.add(cashOption);
        panel.add(Box.createVerticalStrut(30));

        // QR Code display
        JPanel qrPanel = new JPanel();
        qrPanel.setBackground(Color.WHITE);
        qrPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        qrPanel.setPreferredSize(new Dimension(250, 250));
        qrPanel.setMaximumSize(new Dimension(250, 250));
        qrPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel qrLabel = new JLabel(createQRCode());
        qrLabel.setHorizontalAlignment(SwingConstants.CENTER);
        qrPanel.add(qrLabel);

        panel.add(qrPanel);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createPaymentOption(String optionName, String type) {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        panel.setMaximumSize(new Dimension(450, 60));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Icon
        JLabel iconLabel = new JLabel();
        if (type.equals("qr_promptpay")) {
            iconLabel.setText("💳");
            iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        } else {
            iconLabel.setText("💵");
            iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        }

        // Option name
        JLabel nameLabel = new JLabel(optionName);
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        // Subtitle
        JLabel subtitleLabel = new JLabel(type.equals("qr_promptpay") ? "QR PromptPay" : "เงินสด");
        subtitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.GRAY);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.add(nameLabel);
        textPanel.add(subtitleLabel);

        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textPanel, BorderLayout.CENTER);

        // Hover effect
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = panel.getBackground();

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                panel.setBackground(new Color(240, 248, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                panel.setBackground(originalColor);
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!termsCheckbox.isSelected()) {
                    JOptionPane.showMessageDialog(PaymentFrame.this,
                            "กรุณายอมรับข้อตกลงก่อนดำเนินการชำระเงิน",
                            "แจ้งเตือน",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(PaymentFrame.this,
                        "ชำระเงินสำเร็จ!\n" +
                                "วิธีการ: " + optionName + "\n" +
                                "วันที่จอง: " + bookingDate + "\n" +
                                "เวลาจอง: " + bookingTime + "\n" +
                                "ยอดชำระ: ฿" + String.format("%,d", totalAmount),
                        "สำเร็จ",
                        JOptionPane.INFORMATION_MESSAGE);

                // Close and return to home
                dispose();
            }
        });

        return panel;
    }

    private ImageIcon createQRCode() {
        // Create a simple QR code placeholder
        int size = 220;
        java.awt.image.BufferedImage img = new java.awt.image.BufferedImage(size, size, java.awt.image.BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // White background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, size, size);

        // Create QR pattern
        g2.setColor(Color.BLACK);
        int blockSize = 10;

        // Create a simple QR-like pattern
        for (int i = 0; i < size / blockSize; i++) {
            for (int j = 0; j < size / blockSize; j++) {
                // Random pattern for demonstration
                if ((i + j) % 3 == 0 || (i * j) % 5 == 0) {
                    g2.fillRect(i * blockSize, j * blockSize, blockSize, blockSize);
                }
            }
        }

        // Add corner markers
        int cornerSize = blockSize * 3;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, cornerSize, cornerSize);
        g2.fillRect(size - cornerSize, 0, cornerSize, cornerSize);
        g2.fillRect(0, size - cornerSize, cornerSize, cornerSize);

        g2.setColor(Color.WHITE);
        g2.fillRect(blockSize, blockSize, blockSize, blockSize);
        g2.fillRect(size - cornerSize + blockSize, blockSize, blockSize, blockSize);
        g2.fillRect(blockSize, size - cornerSize + blockSize, blockSize, blockSize);

        g2.dispose();
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            // Test data
            List<BookingFrame.OrderItem> items = new java.util.ArrayList<>();
            items.add(new BookingFrame.OrderItem("กางเกง", "ซัก", 1, 40));
            items.add(new BookingFrame.OrderItem("เครื่องนอน", "ซัก", 2, 40));
            items.add(new BookingFrame.OrderItem("เสื้อคลุม", "ซัก", 2, 40));
            items.add(new BookingFrame.OrderItem("เสื้อ", "ซัก", 2, 40));

            PaymentFrame frame = new PaymentFrame(items, 280, "01/12/2024", "07:28 PM");
            frame.setVisible(true);
        });
    }
}
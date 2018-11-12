using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MySql.Data.MySqlClient;

namespace LibarayManage
{
    public partial class AddBook : Form
    {
        private MySqlConnection conn;
        public AddBook()
        {
            InitializeComponent();
        }
        /*
         * 连接数据库
         */
        public void ConnectMariaDB()
        {
            string connectionString = "server=localhost;user id=root;password=donky@16;database=libaraymanage";
            this.conn = new MySqlConnection(connectionString);
            conn.Open();
        }
        private void button1_Click(object sender, EventArgs e)
        {
            try
            {
                ConnectMariaDB();
                string addBookCommandString = "insert into books values(null,'" + bookNameText.Text + "','" + authorText.Text + "','root');";
                MySqlCommand command = new MySqlCommand(addBookCommandString, conn);
                command.ExecuteNonQuery();
                MessageBox.Show("书籍添加成功！");
            }
            catch
            {
                MessageBox.Show("书籍添加失败！");
            }
            conn.Close();
            Close();
        }
    }
}

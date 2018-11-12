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
    public partial class AdminForm : Form
    {
        private string userName = null;    //用户界面用于验证用户身份的唯一标识

        private MySqlConnection conn = new MySqlConnection();
        private MySqlCommand command = new MySqlCommand();
        private MySqlDataAdapter dataAdapter = new MySqlDataAdapter();
        private DataSet dataSet = new DataSet();
        
        public AdminForm(string userName)
        {
            this.userName = userName;
            InitializeComponent();
            dataGridView1.ReadOnly = true;
            ConnectMariaDB();
            welcomeLable.Text = "你好，" + userName + "!";
        }
        /*
         * 连接数据库
         */
        public void ConnectMariaDB()
        {
            string connectionString = "server=localhost;user id=root;password=donky@16;database=libaraymanage";
            conn = new MySqlConnection(connectionString);
            conn.Open();
        }
        /*
         * 查询所有书籍
         */
        private void button1_Click(object sender, EventArgs e)
        {
            string selectAllBookscommandString = "select * from books;";
            MySqlCommand command = new MySqlCommand(selectAllBookscommandString, conn);
            dataAdapter.SelectCommand = command;
            dataSet.Clear();
            dataAdapter.Fill(dataSet, "AllBooksTable");
            dataGridView1.DataSource = dataSet;
            dataGridView1.DataMember = "AllBooksTable";
        }
        /*
         * 查询未借出书籍
         */
        private void button2_Click(object sender, EventArgs e)
        {
            string selectRootBookscommandString = "select * from books where holder = 'root';";
            command = new MySqlCommand(selectRootBookscommandString, conn);
            dataAdapter.SelectCommand = command;
            dataSet.Clear();
            dataAdapter.Fill(dataSet, "RootBooksTable");
            dataGridView1.DataSource = dataSet;
            dataGridView1.DataMember = "RootBooksTable";
        }
        /*
         * 查询用户已借的书籍
         */
        private void button3_Click(object sender, EventArgs e)
        {
            string selectUserBooksCommandString = "select * from books where holder = '" + userName + "';";
            command = new MySqlCommand(selectUserBooksCommandString, conn);
            dataAdapter.SelectCommand = command;
            dataSet.Clear();
            dataAdapter.Fill(dataSet, "UserBooksTable");
            dataGridView1.DataSource = dataSet;
            dataGridView1.DataMember = "UserBooksTable";
        }
        /* 
         * 模糊查询
         */
        private void button4_Click(object sender, EventArgs e)
        {
            string searchBooksCommandString = null;
            if (comboBox1.Text == "书籍名称")
            {
                searchBooksCommandString = "select * from books where name like '%" + searchText.Text + "%';";
            }
            else
            {
                searchBooksCommandString = "select * from books where author like '%" + searchText.Text + "%';";
            }
            command = new MySqlCommand(searchBooksCommandString, conn);
            dataAdapter.SelectCommand = command;
            dataSet.Clear();
            dataAdapter.Fill(dataSet, "SearchBooksTable");
            dataGridView1.DataSource = dataSet;
            dataGridView1.DataMember = "SearchBooksTable";
        }
        /*
         * 借阅书籍
         */
        private void button5_Click(object sender, EventArgs e)
        {
            string holder = null;
            int id = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells[0].Value);
            string selectBookHolderByIdCommandString = "select holder from books where id = " + id + ";";
            command = new MySqlCommand(selectBookHolderByIdCommandString, conn);
            MySqlDataReader dataReader = command.ExecuteReader();
            while (dataReader.Read())
            {
                holder = dataReader[0].ToString();
            }
            /*
             * conn绑定到DataReader之后，需要关闭重新开启
             * 否则不能直接绑定到MySqlCommand中
             */
            conn.Close();
            conn.Open();
            if (holder == null || holder != "root")
            {
                MessageBox.Show("此书籍不可借阅...");
            }
            else
            {
                try
                {
                    string updateRootBookToUserCommandString = "update books set holder = '" + userName + "' where id = " + id + ";";
                    command = new MySqlCommand(updateRootBookToUserCommandString, conn);
                    command.ExecuteNonQuery();
                    MessageBox.Show("借阅成功...");
                }
                catch
                {
                    MessageBox.Show("借阅未知错误...");
                }
               
            }
            
        }
        /*
         * 归还书籍
         */
        private void button6_Click(object sender, EventArgs e)
        {
            string holder = null;
            int id = Convert.ToInt32(dataGridView1.SelectedRows[0].Cells[0].Value);
            string selectBookHolderByIdCommandString = "select holder from books where id = " + id + ";";
            command = new MySqlCommand(selectBookHolderByIdCommandString, conn);
            MySqlDataReader dataReader = command.ExecuteReader();
            while (dataReader.Read())
            {
                holder = dataReader[0].ToString();
            }
            /*
            * conn绑定到DataReader之后，需要关闭重新开启
            * 否则不能直接绑定到MySqlCommand中
            */
            conn.Close();
            conn.Open();
            if (holder == null || holder != userName)
            {
                MessageBox.Show("此书籍未借阅...");
            }
            else
            {
                try
                {
                    string updateUserBookToRootCommandString = "update books set holder = 'root' where id = " + id + ";";
                    command = new MySqlCommand(updateUserBookToRootCommandString, conn);
                    command.ExecuteNonQuery();
                    MessageBox.Show("归还成功...");
                }
                catch
                {
                    MessageBox.Show("归还未知错误...");
                }

            }
        }
    }
}

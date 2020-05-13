import React from 'react';
import moment from 'moment';
import { Input, Button, Modal, Typography, DatePicker } from 'antd';
import QuestionForm from './QuestionForm';
import {
    DeleteOutlined,
    EditOutlined,
    PlusCircleOutlined,
    CarryOutOutlined,
} from '@ant-design/icons';
import { createPoll } from './client';

const { Title, Paragraph } = Typography;
  
function disabledDate(current) {
    // Can not select days before today and days after 14 days later.
    return current < moment().endOf('day') || current > moment().add(14, 'days');
}

class Main extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            title: '',
            editMod: false,
            currentEditId: -1,
            questions: [],
            endDate: moment().endOf('day'),
        };
    }

    componentDidMount(){
        // console.log(this.state.endDate)
    }

    openEditMod = (QuestionId) => {

        this.setState({
            editMod: true,
            currentEditId: QuestionId,
        });
    };

    closeEditMod = () => {
        this.setState({
            editMod: false,
        });
    }

    onQuestionConfirm = (data) => {
        if (this.state.currentEditId >= 0 ) {
            // edit mod
            const temp = this.state.questions;
            temp[this.state.currentEditId] = data;
            this.setState({
                questions: temp,
                currentEditId: -1,
            });
        } else {
            // create mod
            const temp = [...this.state.questions, data];
            this.setState({
                questions: temp,
            });
        }
        this.closeEditMod();
    }

    onQuestionDeletion = (index) => {
        const temp = [...this.state.questions];
        temp.splice(index, 1);
        this.setState({
            questions: temp,
        });
    }

    renderQuestions = (questions) => {
        return questions.map((question, index) => {
            const options = question.options.map((option, i) => <div className='option' key={i + option.name}>option {i+1}:{option.name}</div>);
            return (
                <div key={index + question.name}>
                    <div className='question_title'>
                        Question {index+1}: 
                        <span className='blue'>({question.type==='MULTIPLE'? 'Multiple choice' : 'Single choice'})</span> 
                        {question.name}
                        <DeleteOutlined onClick={this.onQuestionDeletion.bind(this, index)} />
                        <EditOutlined onClick={this.openEditMod.bind(this, index)} />
                    </div>
                    <div>{options}</div>
                </div>
            );
        });
    }

    onTitleChange = (e) => {
        const { value } = e.target;
        this.setState({
            title: value,
        });
    }

    check = () => {
        return !!this.state.title.trim() && this.state.questions.length > 0
    }

    onCreate = () => {
        const o = {
            title: this.state.title.trim(),
            questions: this.state.questions,
            endTime: this.state.endDate.format('YYYY-MM-D hh:mm:ss'),
        };
        // The response from back-end will be the ID of the new poll
        createPoll(o).then(
            (poll_id) => {
                console.log('new poll ID: ' + poll_id);
                // Now the new poll isready and we got the ID, time to redirect!
                window.location.replace('/admin/' + poll_id); 
            }, 
            (e) => {
                window.location.replace('/error/');
            });
    }

    onDateChange = (date) => {
        this.setState({
            endDate: date,
        });
    }

    setXdaysLater = (x) => {
        this.setState({
            endDate:  moment().endOf('day').add(x, 'days'),
        });
    }

    render() {

        const questions = this.renderQuestions(this.state.questions);
        
        return (
            <React.Fragment>
                <div className="main">
                    <Typography>
                        <Title>EasyPoll</Title>
                        <Paragraph>
                            This is an online polling application. Yout can start a poll among your friends within 1 min. 
                            <ul>
                                <li>Easy to setup</li>
                                <li>No need to sign in</li>
                                <li>Multiple question types</li>
                            </ul>
                        </Paragraph>
                    </Typography>
                    
                    <Title>Start a New Poll</Title>
                    <span>Title:</span>
                    <Input value={this.state.title} onChange={this.onTitleChange} placeholder="Title of your poll" />
                    <div className='questions'>{questions}</div>
                    <div className='date'>
                        <DatePicker
                            format="YYYY-MM-DD"
                            disabledDate={disabledDate}
                            value={this.state.endDate}
                            onChange={this.onDateChange}
                        />
                        <Button className='date-btn' onClick={this.setXdaysLater.bind(this, 1)}>1 day later</Button>
                        <Button className='date-btn' onClick={this.setXdaysLater.bind(this, 3)}>3 days later</Button>
                        <Button className='date-btn' onClick={this.setXdaysLater.bind(this, 7)}>7 days later</Button>
                    </div>
                    <div className='date-text'>This poll expires at <span className='blue'>{this.state.endDate.format('MMMM Do')}</span> 23:59</div>
                    <Button type="primary" onClick={this.openEditMod.bind(this, -1)}><PlusCircleOutlined />Add a new question</Button>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <Button type="primary" 
                        disabled={!this.check()} 
                        onClick={this.onCreate}
                    >
                        <CarryOutOutlined />Create it!
                    </Button>
                </div>
                <Modal
                    title="New Question"
                    visible={this.state.editMod}
                    footer={null}
                    onCancel={this.closeEditMod}
                    maskClosable={false}
                    destroyOnClose={true}
                >
                    <QuestionForm 
                        onCancel={this.closeEditMod} 
                        onConfirm={this.onQuestionConfirm} 
                        questions={this.state.questions}
                        editIndex={this.state.currentEditId}
                    />
                </Modal>
            </React.Fragment>
        );
    }
}

export default Main;

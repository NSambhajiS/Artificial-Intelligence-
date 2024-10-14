import streamlit as st
from groq import Groq

# Function to initialize the Groq client
def initialize_groq_client(api_key):
    try:
        return Groq(api_key=api_key)
    except Exception as e:
        st.error(f"Error initializing Groq client: {e}")
        return None

def socratic_assistant_response(client, input_text, context=None, prompt=None):
    

    system_prompt = f"""
    You are a smart assistant dedicated to providing emotional support and mental wellness resources. Your task is to answer questions and provide guidance related to mental health, emotional well-being, stress management, self-care strategies, and coping mechanisms. Additionally, you should recommend relevant content from trusted sources when users request further reading or resources. 
Your responses should be empathetic, informative, and based on reliable mental health practices. Where applicable, include tips, techniques, or resources for further support. Some possible types of queries include:

1. *Emotional Support*: Offer guidance on how to manage feelings of anxiety, sadness, or stress, and provide comforting strategies for coping.
2. *Stress Management Techniques*: Share practical methods for reducing stress, such as mindfulness exercises, breathing techniques, and relaxation methods.
3. *Self-Care Strategies*: Recommend daily self-care activities that promote mental well-being and encourage positive habits.
4. *Coping Mechanisms*: Provide advice on effective coping strategies for dealing with difficult situations or overwhelming emotions.
5. *Mental Health Resources*: Share information about resources such as hotlines, counseling services, and mental health apps.
6. *Content Recommendations*: Suggest articles, videos, podcasts, and books from trusted mental health websites and organizations when users seek additional information or inspiration.
7. *Wellness Trends*: Update users on recent trends in mental health care, such as new therapeutic approaches or community support initiatives.

Always ensure that your information is compassionate, up-to-date, and tailored to the user's specific needs and concerns.
    """


    conversation = f"{context}\nStudent: {input_text}\nAssistant:" if context else f"Student: {input_text}\nAssistant:"

    try:
        chat_completion = client.chat.completions.create(
            messages=[
                {"role": "system", "content": system_prompt},
                {"role": "user", "content": conversation}
            ],
            model="llama3-70b-8192",
            temperature=0.5
        )
        response = chat_completion.choices[0].message.content
        return response
    except Exception as e:
        st.error(f"Error generating chat completion: {e}")
        return "An error occurred while generating the response."

# Streamlit app
def main():
    st.set_page_config(page_title="Socratic Teaching Assistant", page_icon=":books:")

    st.title("📚 Mentor - Connect Customer Service")

    if "messages" not in st.session_state:
        st.session_state["messages"] = [{"role": "assistant", "content": "How can I help you today?"}]

    # Display chat messages
    conversation_str = "\n".join([f"{m['role'].capitalize()}: {m['content']}" for m in st.session_state.messages])
    for msg in st.session_state.messages:
        if msg["role"] == "assistant":
            st.chat_message("assistant").write(msg["content"])
        elif msg["role"] == "user":
            st.chat_message("user").write(msg["content"])

    # Handle user input
    user_input = st.chat_input("Enter your question or response:")
    if user_input:
        st.session_state.messages.append({"role": "user", "content": user_input})
        st.chat_message("user").write(user_input)

        # Initialize Groq client
        client_groq = initialize_groq_client("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")
        if client_groq is None:
            st.error("Failed to initialize the Groq client. Please check your API key.")
            st.stop()

        # Prepare context
        context = conversation_str

        # Generate assistant response
        try:
            full_response = socratic_assistant_response(client_groq, user_input, context=context)
            st.session_state.messages.append({"role": "assistant", "content": full_response})
            st.chat_message("assistant").write(full_response)

        except Exception as e:
            st.error(f"An error occurred while generating the response: {e}")


main()